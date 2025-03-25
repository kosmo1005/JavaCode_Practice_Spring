package com.kulushev.app.exception.exceptionHandler;

import com.kulushev.app.exception.notFound.OrderNotFoundException;
import com.kulushev.app.exception.UserAlreadyExist;
import com.kulushev.app.exception.notFound.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class,
            HttpMessageNotReadableException.class})
    public ModelAndView illegalArgumentException(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 400);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 400);
        modelAndView.addObject("errorMessage", errors);
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessDeniedException(AccessDeniedException ex) {

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 403);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = {UserNotFoundException.class, OrderNotFoundException.class})
    public ModelAndView notFoundException(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 404);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ModelAndView alreadyExistException(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 409);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView unknownException(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("statusCode", 500);
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

}
