package com.technokratos.mapper;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.*;
import com.technokratos.model.jooq.enums.Gender;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.security.AuthenticationPrinciple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mapping(target = "hashPassword", source = "password", qualifiedByName = "hash")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    AccountEntity getAccountEntity(AccountSignUpRequest request);

    UserResponse getUserResponse(AccountEntity account, String imageName);

    AccountResponse getAccountResponse(AccountEntity account, String imageName);

    AccountResponse getAccountResponse(AuthenticationPrinciple principle, String imageName);

    AccountInfoResponse getAccountInfoResponse(AuthenticationPrinciple principle);

    default SubscribersResponse getSubscribers(List<String> subscribers) {
        int count = subscribers.size();
        return SubscribersResponse.builder()
                .count(count)
                .subscribers(subscribers)
                .build();
    }

    default SubscriptionsResponse getSubscriptions(List<String> subscriptions) {
        int count = subscriptions.size();
        return SubscriptionsResponse.builder()
                .count(count)
                .subscriptions(subscriptions)
                .build();
    }

    default AccountEntity getAccountEntity(AccountEditRequest request, AccountEntity account) {
        List<String> fields = request.getFields();
        for (String field : fields) {
            switch (field) {
                case "email":
                    account.setEmail(request.getEmail());
                    break;
                case "username":
                    account.setUsername(request.getUsername());
                    break;
                case "firstName":
                    account.setFirstName(request.getFirstName());
                    break;
                case "lastName":
                    account.setLastName(request.getLastName());
                    break;
                case "gender":
                    account.setGender(Gender.valueOf(request.getGender()));
                    break;
                case "aboutMe":
                    account.setAboutMe(request.getAboutMe());
                    break;
                case "birthDate":
                    account.setBirthDate(request.getBirthDate());
                    break;
            }
        }
        return account;
    }

    @Named("hash")
    default String hash(String password) {
        return passwordEncoder.encode(password);
    }
}
