package com.technokratos.controller.v1;

import com.technokratos.api.v1.UserApi;
import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.dto.response.SubscribersResponse;
import com.technokratos.dto.response.SubscriptionsResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.mapper.UserMapper;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.service.PublicationService;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final PublicationService publicationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserResponse> getUser(String username) {
        UserResponse response = userService.getUserResponseByUsername(username);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SubscribersResponse> getSubscribers(String username) {
        List<String> subscribers = userService.findSubscribersByUsername(username);
        return ResponseEntity.ok(userMapper.getSubscribers(subscribers));
    }

    @Override
    public ResponseEntity<SubscriptionsResponse> getSubscriptions(String username) {
        List<String> subscriptions = userService.findSubscriptionsByUsername(username);
        return ResponseEntity.ok(userMapper.getSubscriptions(subscriptions));
    }

    @Override
    public ResponseEntity<List<PublicationResponse>> getPublications(String username) {
        AccountEntity account = userService.findAccountByUsername(username).orElseThrow(UserNotFoundException::new);
        List<PublicationResponse> publications = publicationService.getPublicationsByAccountId(account.getId());
        return ResponseEntity.ok(publications);
    }
}
