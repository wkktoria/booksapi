package com.example.booksapi.domain.bookcrud;

public class BookExistsException extends RuntimeException {

    public BookExistsException(final String message) {
        super(message);
    }
    
}
