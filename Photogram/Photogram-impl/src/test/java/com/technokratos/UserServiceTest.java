package com.technokratos;

import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.exception.EmailAlreadyExistsException;
import com.technokratos.exception.PasswordMismatchException;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.exception.UsernameAlreadyExistsException;
import com.technokratos.model.jooq.enums.AccountState;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.repository.AccountRepository;
import com.technokratos.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.Util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AccountRepository repository;

    @Autowired
    public void configure() {
        Mockito.doReturn(UUID.randomUUID())
                .when(repository)
                .save(ArgumentMatchers.any(AccountEntity.class));

        Mockito.doReturn(Optional.of(account()))
                .when(repository)
                .findById(ArgumentMatchers.any(UUID.class));

        Mockito.doReturn(Optional.empty())
                .when(repository)
                .findByUsername(ArgumentMatchers.anyString());

        Mockito.doReturn(Optional.empty())
                .when(repository)
                .findByEmail(ArgumentMatchers.anyString());

        Mockito.doNothing()
                .when(repository)
                .update(ArgumentMatchers.any(AccountEntity.class));
    }

    @Test
    public void create() {
        AccountSignUpRequest accountSignUpRequest = userSignUpRequest();
        UUID id = userService.createAccount(accountSignUpRequest);

        assertNotNull(id);

        Mockito.verify(repository, Mockito.times(1))
                .save(ArgumentMatchers.any(AccountEntity.class));
    }

    @Test
    public void updateById() {

        assertNotNull(userService.updateAccountById(UUID.randomUUID(), userEditRequest()));

        Mockito.verify(repository, Mockito.times(1))
                .findById(ArgumentMatchers.any(UUID.class));

        Mockito.verify(repository, Mockito.times(1))
                .findByUsername(ArgumentMatchers.anyString());

        Mockito.verify(repository, Mockito.times(1))
                .findByEmail(ArgumentMatchers.anyString());

        Mockito.verify(repository, Mockito.times(1))
                .update(ArgumentMatchers.any(AccountEntity.class));
    }

    @Test
    public void updateByIdUserNotFound() {

        Mockito.doThrow(UserNotFoundException.class)
                .when(repository)
                .findById(ArgumentMatchers.any(UUID.class));

        assertThrows(UserNotFoundException.class,
                () -> userService.updateAccountById(UUID.randomUUID(), userEditRequest()));
    }

    @Test
    public void updateByIdUsernameAlreadyExists() {

        Mockito.doThrow(UsernameAlreadyExistsException.class)
                .when(repository)
                .findByUsername(ArgumentMatchers.anyString());

        assertThrows(UsernameAlreadyExistsException.class,
                () -> userService.updateAccountById(UUID.randomUUID(), userEditRequest()));
    }

    @Test
    public void updateByIdEmailAlreadyExists() {

        Mockito.doThrow(EmailAlreadyExistsException.class)
                .when(repository)
                .findByEmail(ArgumentMatchers.anyString());

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.updateAccountById(UUID.randomUUID(), userEditRequest()));
    }

    @Test
    public void changePasswordById() {

        String oldPassword = "";

        AccountEntity account = account();
        account.setHashPassword(passwordEncoder.encode(oldPassword));

        Mockito.doReturn(Optional.of(account))
                .when(repository)
                .findById(ArgumentMatchers.any(UUID.class));

        userService.changeAccountPasswordById(UUID.randomUUID(), oldPassword, "");

        Mockito.verify(repository, Mockito.times(1))
                .update(ArgumentMatchers.any(AccountEntity.class));
    }

    @Test
    public void changePasswordByIdPasswordMissmatch() {
        assertThrows(PasswordMismatchException.class,
                () -> userService.changeAccountPasswordById(UUID.randomUUID(), "", ""));
    }

    @Test
    public void findByEmail() {
        assertNotNull(userService.findAccountByEmail(""));
        Mockito.verify(repository, Mockito.times(1))
                .findByEmail(ArgumentMatchers.anyString());
    }

    @Test
    public void findById() {
        assertNotNull(userService.findAccountById(UUID.randomUUID()));
        Mockito.verify(repository, Mockito.times(1))
                .findById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    void findByUsername() {
        assertNotNull(userService.findAccountByUsername(""));
        Mockito.verify(repository, Mockito.times(1))
                .findByUsername(ArgumentMatchers.anyString());
    }

    @Test
    void confirmById() {
        AccountEntity account = account();
        account.setState(AccountState.NOT_CONFIRMED);

        Mockito.doReturn(Optional.of(account))
                .when(repository)
                .findByEmail(ArgumentMatchers.anyString());

        userService.confirmAccountByEmail("");

        assertEquals(AccountState.CONFIRMED, account.getState());

        Mockito.verify(repository, Mockito.times(1))
                .update(ArgumentMatchers.any());
    }

    @Test
    void deleteById() {
        userService.deleteAccountById(UUID.randomUUID());
        Mockito.verify(repository, Mockito.times(1))
                .deleteById(ArgumentMatchers.any(UUID.class));
    }
}
