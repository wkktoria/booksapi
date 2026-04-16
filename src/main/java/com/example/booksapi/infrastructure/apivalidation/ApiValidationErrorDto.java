package com.example.booksapi.infrastructure.apivalidation;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
record ApiValidationErrorDto(String message, HttpStatus status) {
}
