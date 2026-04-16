package com.example.booksapi.domain.bookcrud.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record AllBooksResponseDto(Set<BookDto> books) {
}
