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

        // Проверяем имя
        if (user.name() == null || !user.name().matches("^[A-Z][a-zA-Z]* [A-Z][a-zA-Z]*$")) {
            context.buildConstraintViolationWithTemplate("Name must consist of two words, each starting with a capital letter, separated by a space")
                    .addPropertyNode("name") // Указываем, что ошибка относится к полю name
                    .addConstraintViolation();
            isValid = false;
        }

        // Проверяем email
        if (user.email() == null || !user.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            context.buildConstraintViolationWithTemplate("Invalid email format")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}

