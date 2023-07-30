package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_FIRST_NAME_LENGTH;

@Component
public class FirstNameValidator implements Validator<String> {

    @Override
    public void validate(String firstName) throws RuntimeException {
        if (firstName == null || firstName.isEmpty()) {
            throw new BadRequestException("First name must not be empty");
        }

        if (firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new BadRequestException(String.format("First name length must not be more than %d", MAX_FIRST_NAME_LENGTH));
        }
    }
}
