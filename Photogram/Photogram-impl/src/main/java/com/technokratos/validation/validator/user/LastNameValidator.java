package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_LAST_NAME_LENGTH;

@Component
public class LastNameValidator implements Validator<String> {

    @Override
    public void validate(String lastName) throws RuntimeException {
        if (lastName == null || lastName.isEmpty()) {
            throw new BadRequestException("Last name must not be empty");
        }
        if (lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new BadRequestException(String.format("Last name length must not be more than %d", MAX_LAST_NAME_LENGTH));
        }
    }
}
