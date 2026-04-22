package com.example.booksapi.domain.bookcrud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
class BookUpdater {

    private final BookRetriever bookRetriever;

    @Transactional
    Book update(final Long id, final String title) {
        if (bookRetriever.existsByTitle(title)) {
            log.warn("Could not update book - book with title='{}' already exists", title);
            throw new BookExistsException("Book with the given title already exists");
        }

        Book bookToUpdate = bookRetriever.retrieveById(id);
        bookToUpdate.setTitle(title);
        return bookToUpdate;
    }

}
