package com.technokratos.validation.validator.user;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_ABOUT_ME_LENGTH;

@Component
public class AboutMeValidator implements Validator<String> {

    @Override
    public void validate(String aboutMe) throws RuntimeException {
        if (aboutMe != null && aboutMe.length() > MAX_ABOUT_ME_LENGTH) {
            throw new BadRequestException(String.format("About me length must not be more than %d", MAX_ABOUT_ME_LENGTH));
        }
    }
}
