package com.example.booksapi.domain.bookcrud;

import com.example.booksapi.domain.bookcrud.dto.BookDto;

class BookMapper {

    static BookDto mapFromBookToBookDto(final Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .build();
    }

}
