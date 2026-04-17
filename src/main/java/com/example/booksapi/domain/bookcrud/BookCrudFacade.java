package com.example.booksapi.domain.bookcrud;

import com.example.booksapi.domain.bookcrud.dto.AllBooksResponseDto;
import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.CreateBookRequestDto;
import com.example.booksapi.domain.bookcrud.dto.UpdateBookRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.booksapi.domain.bookcrud.BookMapper.mapFromBookToBookDto;

@Component
@RequiredArgsConstructor
@Log4j2
public class BookCrudFacade {

    private final BookAdder bookAdder;
    private final BookRetriever bookRetriever;
    private final BookUpdater bookUpdater;

    public BookDto createBook(final CreateBookRequestDto requestDto) {
        Book createdBook = bookAdder.addBook(requestDto.title());
        return mapFromBookToBookDto(createdBook);
    }

    public AllBooksResponseDto findAllBooks(final Pageable pageable) {
        Set<Book> allBooks = bookRetriever.retrieveAllBooks(pageable);
        Set<BookDto> bookDtos = allBooks.stream()
                .map(BookMapper::mapFromBookToBookDto)
                .collect(Collectors.toSet());
        return AllBooksResponseDto.builder()
                .books(bookDtos)
                .build();
    }

    public BookDto findBookById(final Long id) {
        Book book = bookRetriever.retrieveById(id);
        return mapFromBookToBookDto(book);
    }

    public BookDto updateBook(final Long id, final UpdateBookRequestDto requestDto) {
        Book updatedBook = bookUpdater.update(id, requestDto.title());
        return mapFromBookToBookDto(updatedBook);
    }

}
