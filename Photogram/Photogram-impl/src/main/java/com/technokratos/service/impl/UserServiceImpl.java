package com.technokratos.service.impl;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AccountInfoResponse;
import com.technokratos.dto.response.AccountResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.exception.EmailAlreadyExistsException;
import com.technokratos.exception.PasswordMismatchException;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.exception.UsernameAlreadyExistsException;
import com.technokratos.mapper.UserMapper;
import com.technokratos.model.jooq.enums.AccountState;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.repository.AccountRepository;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.service.FileService;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FileService fileService;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID createAccount(AccountSignUpRequest request) {
        AccountEntity account = userMapper.getAccountEntity(request);
        return accountRepository.save(account);
    }

    @Override
    public UserResponse getUserResponseByUsername(String username) {
        AccountEntity account = accountRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return userMapper.getUserResponse(account, fileService.getAccountImageNameByAccountId(account.getId()));
    }

    @Override
    public AccountResponse getAccountResponse(AuthenticationPrinciple principle) {
        return userMapper.getAccountResponse(principle, fileService.getAccountImageNameByAccountId(principle.getId()));
    }

    @Override
    public AccountInfoResponse getAccountInfoResponse(AuthenticationPrinciple principle) {
        return userMapper.getAccountInfoResponse(principle);
    }

    @Override
    public AccountResponse subscribeByUsername(UUID id, String username) {
        AccountEntity account = accountRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        String imageName = fileService.getAccountImageNameByAccountId(id);
        if (id.equals(account.getId())) {
            return userMapper.getAccountResponse(account, imageName);
        }
        List<String> subscriptions = findSubscriptionsById(id);
        if (!subscriptions.contains(username)) {
            accountRepository.subscribe(id, account.getId());
        }
        account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.getAccountResponse(account, imageName);
    }

    @Override
    public AccountResponse unsubscribeByUsername(UUID id, String username) {
        AccountEntity account = accountRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        String imageName = fileService.getAccountImageNameByAccountId(id);
        if (id.equals(account.getId())) {
            return userMapper.getAccountResponse(account, imageName);
        }
        accountRepository.unsubscribe(id, account.getId());
        account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.getAccountResponse(account, imageName);
    }

    @Override
    public List<String> findSubscribersById(UUID id) {
        return accountRepository.findSubscribersById(id);
    }

    @Override
    public List<String> findSubscriptionsById(UUID id) {
        return accountRepository.findSubscriptionsById(id);
    }

    @Override
    public List<String> findSubscribersByUsername(String username) {
        return accountRepository.findSubscribersByUsername(username);
    }

    @Override
    public List<String> findSubscriptionsByUsername(String username) {
        return accountRepository.findSubscriptionsByUsername(username);
    }

    @Override
    public Optional<AccountEntity> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<AccountEntity> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<AccountEntity> findAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountResponse updateAccountById(UUID id, AccountEditRequest request) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<String> fields = request.getFields();
        if (fields.contains("username")) {
            accountRepository.findByUsername(request.getUsername()).ifPresent(object -> {
                throw new UsernameAlreadyExistsException();
            });
        }
        if (fields.contains("email")) {
            accountRepository.findByEmail(request.getEmail()).ifPresent(object -> {
                throw new EmailAlreadyExistsException();
            });
        }
        account = userMapper.getAccountEntity(request, account);
        account.setUpdatedDate(LocalDateTime.now());
        accountRepository.update(account);
        return userMapper.getAccountResponse(account, fileService.getAccountImageNameByAccountId(id));
    }

    @Override
    public void changeAccountPasswordById(UUID id, String oldPassword, String newPassword) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(oldPassword, account.getHashPassword())) {
            throw new PasswordMismatchException();
        }
        account.setHashPassword(passwordEncoder.encode(newPassword));
        accountRepository.update(account);
    }

    @Override
    public void confirmAccountByEmail(String email) {
        AccountEntity account = accountRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        account.setState(AccountState.CONFIRMED);
        accountRepository.update(account);
    }

    @Override
    public void deleteAccountById(UUID id) {
        accountRepository.deleteById(id);
    }
}
