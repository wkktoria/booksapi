package com.example.booksapi.infrastructure.security;

public class UserExistsException extends RuntimeException {

    public UserExistsException(final String message) {
        super(message);
    }

}
