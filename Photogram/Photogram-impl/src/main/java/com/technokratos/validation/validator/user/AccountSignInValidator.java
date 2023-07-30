package com.technokratos.validation.validator.user;

import com.technokratos.dto.request.AccountSignInRequest;
import com.technokratos.validation.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSignInValidator implements Validator<AccountSignInRequest> {

    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public void validate(AccountSignInRequest request) throws RuntimeException {
        emailValidator.validate(request.getEmail());
        passwordValidator.validate(request.getPassword());
    }
}
