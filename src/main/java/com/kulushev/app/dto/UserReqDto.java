package com.kulushev.app.dto;

import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.validators.GlobalValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


/**
 * Request DTO for {@link UserEntity}
 */
@GlobalValidator
public record UserReqDto(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Email must not be blank")
        String email
) {
    /*public UserReqDto{
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        if (!name.matches("^[A-Z][a-zA-Z]* [A-Z][a-zA-Z]*$")) {
            throw new IllegalArgumentException
                    ("Name must consist of two words, " +
                            "each starting with a capital letter, " +
                            "separated by a space");
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }*/

}