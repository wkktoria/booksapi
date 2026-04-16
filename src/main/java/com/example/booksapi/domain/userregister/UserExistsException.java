package com.example.booksapi.domain.userregister;

public class UserExistsException extends RuntimeException {

    public UserExistsException(final String message) {
        super(message);
    }

}
