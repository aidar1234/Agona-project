package com.technokratos.validation.validator.user;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.exception.BadRequestException;
import com.technokratos.validation.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountEditValidator implements Validator<AccountEditRequest> {

    private final EmailValidator emailValidator;
    private final UsernameValidator usernameValidator;
    private final FirstNameValidator firstNameValidator;
    private final LastNameValidator lastNameValidator;
    private final GenderValidator genderValidator;
    private final AboutMeValidator aboutMeValidator;
    private final BirthDateValidator birthDateValidator;

    @Override
    public void validate(AccountEditRequest request) {
        List<String> fields = request.getFields();
        if (fields == null || fields.isEmpty()) {
            throw new BadRequestException("Fields list must not be empty");
        }

        for (String field : fields) {
            switch (field) {
                case "email":
                    emailValidator.validate(request.getEmail());
                    break;
                case "username":
                    usernameValidator.validate(request.getUsername());
                    break;
                case "firstName":
                    firstNameValidator.validate(request.getFirstName());
                    break;
                case "lastName":
                    lastNameValidator.validate(request.getLastName());
                    break;
                case "gender":
                    genderValidator.validate(request.getGender());
                    break;
                case "aboutMe":
                    aboutMeValidator.validate(request.getAboutMe());
                    break;
                case "birthDate":
                    birthDateValidator.validate(request.getBirthDate());
                    break;
                default:
                    throw new BadRequestException("No such field");
            }
        }
    }
}
