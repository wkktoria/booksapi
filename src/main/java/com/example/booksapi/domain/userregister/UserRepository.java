package com.example.booksapi.domain.userregister;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    boolean existsByUsername(final String username);

    Optional<User> findByUsername(final String username);

    User save(final User user);

}
