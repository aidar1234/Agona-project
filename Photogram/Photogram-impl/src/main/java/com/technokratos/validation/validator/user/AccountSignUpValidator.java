package com.technokratos.validation.validator.user;

import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.validation.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSignUpValidator implements Validator<AccountSignUpRequest> {

    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final UsernameValidator usernameValidator;
    private final FirstNameValidator firstNameValidator;
    private final LastNameValidator lastNameValidator;
    private final GenderValidator genderValidator;
    private final AboutMeValidator aboutMeValidator;
    private final BirthDateValidator birthDateValidator;

    @Override
    public void validate(AccountSignUpRequest request) {
        emailValidator.validate(request.getEmail());
        passwordValidator.validate(request.getPassword());
        usernameValidator.validate(request.getUsername());
        firstNameValidator.validate(request.getFirstName());
        lastNameValidator.validate(request.getLastName());
        genderValidator.validate(request.getGender());
        aboutMeValidator.validate(request.getAboutMe());
        birthDateValidator.validate(request.getBirthDate());
    }
}
