package com.example.booksapi.infrastructure.apivalidation;

import lombok.Builder;

import java.util.List;

@Builder
public record ApiValidationErrorDto(
        String message,
        int status,
        String error,
        List<String> errors) {
}
