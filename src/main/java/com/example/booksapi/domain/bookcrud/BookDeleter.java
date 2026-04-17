package com.example.booksapi.domain.bookcrud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
class BookDeleter {

    private final BookRetriever bookRetriever;
    private final BookRepository bookRepository;

    @Transactional
    void deleteById(final Long id) {
        Book bookToDelete = bookRetriever.retrieveById(id);
        bookRepository.deleteById(bookToDelete.getId());
    }

}
