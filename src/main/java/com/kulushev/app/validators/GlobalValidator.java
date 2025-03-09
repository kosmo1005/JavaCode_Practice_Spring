package com.kulushev.app.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserReqDtoValidator.class) // Указываем, какой класс будет валидатором
@Target(ElementType.TYPE) // Аннотация применяется ко всему классу (record)
@Retention(RetentionPolicy.RUNTIME) // Аннотация должна работать во время выполнения
public @interface GlobalValidator {
    String message() default "Invalid data"; // Сообщение по умолчанию

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
