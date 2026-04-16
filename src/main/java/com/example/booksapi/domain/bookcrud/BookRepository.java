package com.example.booksapi.domain.bookcrud;

import org.springframework.data.repository.Repository;

interface BookRepository extends Repository<Book, Long> {

    boolean existsByTitle(final String title);

    Book save(final Book book);

}
