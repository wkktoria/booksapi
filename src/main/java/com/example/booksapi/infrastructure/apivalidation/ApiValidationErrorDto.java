package com.example.booksapi.infrastructure.apivalidation;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
public record ApiValidationErrorDto(int status,
                                    String error,
                                    List<String> errors) {
}
