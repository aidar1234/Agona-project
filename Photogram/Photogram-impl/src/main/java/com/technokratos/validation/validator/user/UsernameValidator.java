package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_USERNAME_LENGTH;

@Component
public class UsernameValidator implements Validator<String> {

    @Override
    public void validate(String username) throws RuntimeException {
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Username must not be empty");
        }
        if (username.length() > MAX_USERNAME_LENGTH) {
            throw new BadRequestException(String.format("Username length must not be more than %d", MAX_USERNAME_LENGTH));
        }
    }
}
