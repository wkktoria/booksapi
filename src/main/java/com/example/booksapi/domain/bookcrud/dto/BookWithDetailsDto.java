package com.example.booksapi.domain.bookcrud.dto;

import com.example.booksapi.domain.bookinfofetcher.dto.BookInfoDto;
import lombok.Builder;

@Builder
public record BookWithDetailsDto(BookDto book, BookInfoDto details) {
}
