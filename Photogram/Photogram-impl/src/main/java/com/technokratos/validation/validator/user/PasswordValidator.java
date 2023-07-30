package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_PASSWORD_LENGTH;
import static com.technokratos.validation.util.ValidationConstants.MIN_PASSWORD_LENGTH;

@Component
public class PasswordValidator implements Validator<String> {

    @Override
    public void validate(String password) throws RuntimeException {
        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Password must not be empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new BadRequestException(String.format("Password length must be in range %d..%d", MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        }
    }
}
