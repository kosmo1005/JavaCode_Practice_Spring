package com.kulushev.app.validators;

import com.kulushev.app.dto.OrderReqDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class OrderReqDtoValidator implements ConstraintValidator<GlobalValidator, OrderReqDto> {

    @Override
    public boolean isValid(OrderReqDto order, ConstraintValidatorContext context) {
        if (order == null) return false;

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        // Проверяем UUID
        if (order.userId() == null || !isValidUUID(order.userId())) {
            context.buildConstraintViolationWithTemplate("Invalid UUID format for userId.")
                    .addPropertyNode("userId")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

