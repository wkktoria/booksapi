package com.example.booksapi.domain.bookcrud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
class BookAdder {

    private final BookRepository bookRepository;
    private final BookRetriever bookRetriever;

    Book addBook(final String title) {
        if (bookRetriever.existsByTitle(title)) {
            log.warn("Could not add book - book with title='{}' already exists", title);
            throw new BookExistsException("Book already exists");
        }

        Book book = new Book(title);
        return bookRepository.save(book);
    }

}
