package com.technokratos.controller.v1;

import com.technokratos.api.v1.AccountApi;
import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountPasswordChangeRequest;
import com.technokratos.dto.response.*;
import com.technokratos.mapper.UserMapper;
import com.technokratos.security.AuthUtil;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.service.FileService;
import com.technokratos.service.PublicationService;
import com.technokratos.service.UserService;
import com.technokratos.validation.validator.file.AccountImageValidator;
import com.technokratos.validation.validator.user.AccountEditValidator;
import com.technokratos.validation.validator.user.PasswordValidator;
import com.technokratos.validation.validator.user.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final UserService userService;
    private final FileService fileService;
    private final PublicationService publicationService;
    private final AccountEditValidator editValidator;
    private final PasswordValidator passwordValidator;
    private final UsernameValidator usernameValidator;
    private final AccountImageValidator imageValidator;
    private final AuthUtil authUtil;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<AccountResponse> getAccount() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        AccountResponse response = userService.getAccountResponse(principle);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AccountInfoResponse> getAccountInfo() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        AccountInfoResponse response = userService.getAccountInfoResponse(principle);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SubscribersResponse> getSubscribers() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        List<String> subscribers = userService.findSubscribersById(principle.getId());
        return ResponseEntity.ok(userMapper.getSubscribers(subscribers));
    }

    @Override
    public ResponseEntity<SubscriptionsResponse> getSubscriptions() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        List<String> subscriptions = userService.findSubscriptionsById(principle.getId());
        return ResponseEntity.ok(userMapper.getSubscriptions(subscriptions));
    }

    @Override
    public ResponseEntity<AccountResponse> subscribe(String username) {
        usernameValidator.validate(username);
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        AccountResponse response = userService.subscribeByUsername(principle.getId(), username);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AccountResponse> unsubscribe(String username) {
        usernameValidator.validate(username);
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        AccountResponse response = userService.unsubscribeByUsername(principle.getId(), username);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccount(AccountEditRequest request) {
        editValidator.validate(request);
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        AccountResponse response = userService.updateAccountById(principle.getId(), request);
        return ResponseEntity.ok(response);
    }

    @Override
    public void changePassword(AccountPasswordChangeRequest request) {
        passwordValidator.validate(request.getNewPassword());
        passwordValidator.validate(request.getOldPassword());
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        userService.changeAccountPasswordById(principle.getId(), request.getOldPassword(), request.getNewPassword());
    }

    @Override
    public ResponseEntity<AccountResponse> changeAccountImage(MultipartFile file) {
        imageValidator.validate(file);
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        String imageName = fileService.updateAccountImageByAccountId(principle.getId(), file);
        AccountResponse response = userMapper.getAccountResponse(principle, imageName);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<PublicationResponse>> getPublications() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        List<PublicationResponse> publications = publicationService.getPublicationsByAccountId(principle.getId());
        return ResponseEntity.ok(publications);
    }

    @Override
    public void deleteAccount() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        userService.deleteAccountById(principle.getId());
    }
}
