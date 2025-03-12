package com.kulushev.app.exception.exceptionHandler;

import com.kulushev.app.exception.ErrorResponseDto;
import com.kulushev.app.exception.OrderNotFoundException;
import com.kulushev.app.exception.UserAlreadyExist;
import com.kulushev.app.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> unknownException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(500).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ErrorResponseDto> alreadyExistException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(value = {UserNotFoundException.class, OrderNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> notFoundException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto> illegalArgumentException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errors);

        return ResponseEntity.status(400).body(body);
    }

}
