package com.example.booksapi.domain.userregister.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
