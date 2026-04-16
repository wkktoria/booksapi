package com.example.booksapi.domain.bookcrud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface BookRepository extends Repository<Book, Long> {

    boolean existsByTitle(final String title);

    Book save(final Book book);

    Set<Book> findAll(final Pageable pageable);

    Optional<Book> findById(final Long id);

}
