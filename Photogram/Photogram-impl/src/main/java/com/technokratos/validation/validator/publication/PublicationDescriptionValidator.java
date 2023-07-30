package com.technokratos.validation.validator.publication;

import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import org.springframework.stereotype.Component;

import static com.technokratos.validation.util.ValidationConstants.MAX_PUBLICATION_DESCRIPTION_LENGTH;

@Component
public class PublicationDescriptionValidator implements Validator<String> {

    @Override
    public void validate(String description) throws RuntimeException {
        if (description == null || description.isEmpty()) {
            throw new BadRequestException("Description not must be empty");
        }
        if (description.length() > 512) {
            throw new BadRequestException(String.format("Description length must not be more than %d", MAX_PUBLICATION_DESCRIPTION_LENGTH));
        }
    }
}
