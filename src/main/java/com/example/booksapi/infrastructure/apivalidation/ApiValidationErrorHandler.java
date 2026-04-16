package com.example.booksapi.infrastructure.apivalidation;

import com.example.booksapi.domain.userregister.UserExistsException;
import com.example.booksapi.domain.userregister.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
class ApiValidationErrorHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    ApiValidationErrorDto handleUserExistsException(final UserExistsException exception) {
        return ApiValidationErrorDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    ApiValidationErrorDto handleUserNotFound(final UserNotFoundException exception) {
        return ApiValidationErrorDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ApiValidationErrorDto handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Field '%s': %s",
                        error.getField(), error.getDefaultMessage()))
                .toList();

        return ApiValidationErrorDto.builder()
                .message("Validation failed")
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build();
    }

}
