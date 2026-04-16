package com.example.booksapi.domain.bookcrud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
class BookRetriever {

    private final BookRepository bookRepository;

    boolean existsByTitle(final String title) {
        return bookRepository.existsByTitle(title);
    }

}
