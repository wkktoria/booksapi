package com.example.booksapi.infrastructure.userregister.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotNull
        String username,

        @NotBlank
        String password
) {
}
