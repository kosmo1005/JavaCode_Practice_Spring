package com.kulushev.app.validators;

import com.kulushev.app.dto.GoodReqDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class GoodReqDtoValidator implements ConstraintValidator<GlobalValidator, GoodReqDto> {

    @Override
    public boolean isValid(GoodReqDto good, ConstraintValidatorContext context) {
        if (good == null) return false;

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        // Проверяем имя
        if (good.name() == null || !good.name().matches("^[\\p{L}0-9 ]+$")) {
            context.buildConstraintViolationWithTemplate("Good name must consist only of words or numbers.")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            isValid = false;
        }

        // Проверяем email
        if (good.price() == null || good.price().compareTo(BigDecimal.ZERO) < 0) {
            context.buildConstraintViolationWithTemplate("Price must be non-negative")
                    .addPropertyNode("price")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}

