package com.technokratos.service;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AccountInfoResponse;
import com.technokratos.dto.response.AccountResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.security.AuthenticationPrinciple;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UUID createAccount(AccountSignUpRequest request);

    UserResponse getUserResponseByUsername(String username);

    AccountResponse getAccountResponse(AuthenticationPrinciple principle);

    AccountInfoResponse getAccountInfoResponse(AuthenticationPrinciple principle);

    AccountResponse subscribeByUsername(UUID id, String username);

    AccountResponse unsubscribeByUsername(UUID id, String username);

    List<String> findSubscribersById(UUID id);

    List<String> findSubscriptionsById(UUID id);

    List<String> findSubscribersByUsername(String username);

    List<String> findSubscriptionsByUsername(String username);

    Optional<AccountEntity> findAccountByUsername(String username);

    Optional<AccountEntity> findAccountByEmail(String email);

    Optional<AccountEntity> findAccountById(UUID id);

    AccountResponse updateAccountById(UUID id, AccountEditRequest request);

    void changeAccountPasswordById(UUID id, String oldPassword, String newPassword);

    void confirmAccountByEmail(String email);

    void deleteAccountById(UUID id);
}
