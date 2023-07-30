package com.technokratos.security;

import com.technokratos.model.jooq.enums.AccountRole;
import com.technokratos.model.jooq.enums.AccountState;
import com.technokratos.model.jooq.enums.Gender;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthenticationPrinciple {

    private final AccountEntity account;

    public UUID getId() {
        return account.getId();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public String getHashPassword() {
        return account.getHashPassword();
    }

    public String getUsername() {
        return account.getUsername();
    }

    public AccountState getState() {
        return account.getState();
    }

    public AccountRole getRole() {
        return account.getRole();
    }

    public String getFirstName() {
        return account.getFirstName();
    }

    public String getLastName() {
        return account.getLastName();
    }

    public Gender getGender() {
        return account.getGender();
    }

    public String getAboutMe() {
        return account.getAboutMe();
    }

    public LocalDateTime getBirthDate() {
        return account.getBirthDate();
    }

    public LocalDateTime getCreatedDate() {
        return account.getCreatedDate();
    }

    public LocalDateTime getUpdatedDate() {
        return account.getUpdatedDate();
    }
}
