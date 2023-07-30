package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class GenderValidator implements Validator<String> {

    @Override
    public void validate(String gender) throws RuntimeException {
        if (gender == null || gender.isEmpty()) {
            throw new BadRequestException("Gender name must not be empty");
        }
        if (!gender.matches("MALE|FEMALE")) {
            throw new BadRequestException("Incorrect gender pattern");
        }
    }
}
