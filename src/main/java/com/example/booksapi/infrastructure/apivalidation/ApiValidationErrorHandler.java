package com.example.booksapi.infrastructure.apivalidation;

import com.example.booksapi.domain.bookcrud.BookExistsException;
import com.example.booksapi.domain.bookcrud.BookNotFoundException;
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
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    ApiValidationErrorDto handleUserNotFoundException(final UserNotFoundException exception) {
        return ApiValidationErrorDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BookExistsException.class)
    @ResponseBody
    ApiValidationErrorDto handleBookExistsException(final BookExistsException exception) {
        return ApiValidationErrorDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    ApiValidationErrorDto handleBookNotFoundException(final BookNotFoundException exception) {
        return ApiValidationErrorDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ApiValidationErrorDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Field '%s': %s",
                        error.getField(), error.getDefaultMessage()))
                .toList();

        return ApiValidationErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(errors)
                .build();
    }

}
