package com.example.booksapi.domain.bookcrud;

import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryBookRepository implements BookRepository {

    Map<Long, Book> database = new ConcurrentHashMap<>();
    AtomicLong index = new AtomicLong(0);

    @Override
    public boolean existsByTitle(final String title) {
        return database.values().stream()
                .anyMatch(book -> title.equals(book.getTitle()));
    }

    @Override
    public Book save(final Book book) {
        long index = this.index.getAndIncrement();
        book.setId(index);
        database.put(index, book);
        return book;
    }

    @Override
    public Set<Book> findAll(final Pageable pageable) {
        return new HashSet<>(database.values());
    }

    @Override
    public Optional<Book> findById(final Long id) {
        return Optional.ofNullable(database.get(id));
    }

}
