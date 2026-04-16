package com.example.booksapi.domain.bookcrud;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String message) {
        super(message);
    }

}
