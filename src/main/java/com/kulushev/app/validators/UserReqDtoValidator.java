package com.kulushev.app.validators;

import com.kulushev.app.dto.UserReqDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserReqDtoValidator implements ConstraintValidator<GlobalValidator, UserReqDto> {

    @Override
    public boolean isValid(UserReqDto user, ConstraintValidatorContext context) {
        if (user == null) return false;

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (user.firstName() == null || !user.firstName().matches("^[A-Z][a-zA-Z]*$")) {
            context.buildConstraintViolationWithTemplate(
                    "The first name must consist only of letters, must not contain spaces, and must begin with a capital letter.")
                    .addPropertyNode("firstName")
                    .addConstraintViolation();
            isValid = false;
        }

        if (user.lastName() == null || !user.lastName().matches("^[A-Z][a-zA-Z]*$")) {
            context.buildConstraintViolationWithTemplate(
                            "The last name must consist only of letters, must not contain spaces, and must begin with a capital letter.")
                    .addPropertyNode("lastName")
                    .addConstraintViolation();
            isValid = false;
        }

        if (user.email() == null || !user.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            context.buildConstraintViolationWithTemplate("Invalid email format")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}

