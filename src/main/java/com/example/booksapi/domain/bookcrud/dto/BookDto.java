package com.example.booksapi.domain.bookcrud.dto;

import lombok.Builder;

@Builder
public record BookDto(Long id, String title) {
}
