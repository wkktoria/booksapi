package com.example.booksapi.domain.bookcrud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateBookRequestDto(
        @NotBlank
        String title
) {
}
