package com.example.booksapi.domain.userregister;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryUserRepository implements UserRepository {

    Map<Long, User> database = new ConcurrentHashMap<>();
    AtomicLong index = new AtomicLong(0);

    @Override
    public boolean existsByUsername(final String username) {
        return database.values().stream()
                .anyMatch(user -> username.equals(user.getUsername()));
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return database.values().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    @Override
    public User save(final User user) {
        long index = this.index.getAndIncrement();
        user.setId(index);
        database.put(index, user);
        return user;
    }

}
