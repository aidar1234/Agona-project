package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BirthDateValidator implements Validator<LocalDateTime> {

    @Override
    public void validate(LocalDateTime localDateTime) throws RuntimeException {
        if (localDateTime == null) {
            throw new BadRequestException("Birth date name must not be empty");
        }
    }
}
