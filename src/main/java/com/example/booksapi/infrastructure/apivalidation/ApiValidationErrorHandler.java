package com.example.booksapi.infrastructure.apivalidation;

import com.example.booksapi.infrastructure.security.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

}
