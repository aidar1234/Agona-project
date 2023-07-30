package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_EMAIL_LENGTH;

@Component
@RequiredArgsConstructor
public class EmailValidator implements Validator<String> {

    @Override
    public void validate(String email) throws RuntimeException {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email must not be empty");
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new BadRequestException(String.format("Email length must not be more than %d", MAX_EMAIL_LENGTH));
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email)) {
            throw new BadRequestException("Incorrect email pattern");
        }
    }
}
