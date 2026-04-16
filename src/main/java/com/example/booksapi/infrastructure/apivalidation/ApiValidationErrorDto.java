package com.example.booksapi.infrastructure.apivalidation;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
record ApiValidationErrorDto(String message, HttpStatus status, List<String> errors) {
}
