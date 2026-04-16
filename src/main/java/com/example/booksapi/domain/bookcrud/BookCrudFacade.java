package com.example.booksapi.domain.bookcrud;

import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.CreateBookRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class BookCrudFacade {

    private final BookAdder bookAdder;

    public BookDto createBook(final CreateBookRequestDto requestDto) {
        Book createdBook = bookAdder.addBook(requestDto.title());
        return BookDto.builder()
                .id(createdBook.getId())
                .title(createdBook.getTitle())
                .build();
    }

}
