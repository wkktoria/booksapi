package com.example.booksapi.domain.userregister;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Builder
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Collection<String> authorities = new HashSet<>();

    private boolean enabled = true;

    User(final String username, final String password, final Collection<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

}
