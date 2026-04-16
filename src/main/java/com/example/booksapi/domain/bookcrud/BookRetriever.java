package com.example.booksapi.domain.bookcrud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
class BookRetriever {

    private final BookRepository bookRepository;

    boolean existsByTitle(final String title) {
        return bookRepository.existsByTitle(title);
    }

    Set<Book> retrieveAllBooks(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    Book retrieveById(final Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

}
