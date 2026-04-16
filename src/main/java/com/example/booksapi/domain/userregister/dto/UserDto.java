package com.example.booksapi.domain.userregister.dto;

import lombok.Builder;

import java.util.Collection;

@Builder
public record UserDto(
        Long id,
        String username,
        String password,
        Collection<String> roles,
        boolean enabled) {
}
