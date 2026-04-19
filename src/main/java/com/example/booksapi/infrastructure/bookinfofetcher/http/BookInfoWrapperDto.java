package com.example.booksapi.infrastructure.bookinfofetcher.http;

import com.example.booksapi.domain.bookinfofetcher.dto.BookInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookInfoWrapperDto(
        List<BookInfoDto> docs
) {
}
