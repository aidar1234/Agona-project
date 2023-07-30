package com.technokratos;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AccountResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.mapper.UserMapper;
import com.technokratos.model.jooq.enums.Gender;
import com.technokratos.model.jooq.enums.Role;
import com.technokratos.model.jooq.enums.State;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.security.AuthenticationPrinciple;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Util {

    public static AccountSignUpRequest userSignUpRequest() {
        return AccountSignUpRequest.builder()
                .username("adiar")
                .email("email@com")
                .password("password")
                .firstName("first")
                .lastName("last")
                .aboutMe(null)
                .gender("MALE")
                .birthDate(LocalDateTime.now())
                .build();
    }

    public static AccountEditRequest userEditRequest() {
        return AccountEditRequest.builder()
                .username("adiar")
                .email("email@com")
                .firstName("first")
                .lastName("last")
                .aboutMe(null)
                .gender("MALE")
                .birthDate(LocalDateTime.now())
                .fields(List.of("username", "email", "firstName"))
                .build();
    }

    public static UserResponse userResponse(AccountEntity account) {
        return UserResponse.builder()
                .username(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build();
    }

    public static AccountEntity account() {
        return AccountEntity.builder()
                .id(UUID.fromString("98bdb53a-d67c-43f8-ba96-b878826ae231"))
                .role(Role.USER)
                .state(State.CONFIRMED)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .email("email")
                .hashPassword("$2a$10$ncDqbMcn7ai8/2uy5Go5..6ebOTpw7jphn/bFVXaUyB/NfZ1WSYjW")
                .firstName("first")
                .lastName("last")
                .username("aidar")
                .aboutMe("It's me")
                .gender(Gender.MALE)
                .birthDate(LocalDateTime.now())
                .build();
    }

    public static AccountEntity accountForRequest(AccountSignUpRequest request, UserMapper userMapper) {
        return AccountEntity.builder()
                .id(null)
                .role(null)
                .state(null)
                .createdDate(null)
                .updatedDate(null)
                .email(request.getEmail())
                .hashPassword(userMapper.hash(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .aboutMe(request.getAboutMe())
                .gender(Gender.valueOf(request.getGender()))
                .birthDate(request.getBirthDate())
                .build();
    }

    //public static AccountResponse accountResponse(AccountEntity account) {
    //    return AccountResponse.builder()
    //}
//
    //public static AuthenticationPrinciple authenticationPrinciple(AccountEntity account) {
    //    return new AuthenticationPrinciple(account);
    //}
}
