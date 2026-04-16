package com.example.booksapi.infrastructure.security;

import com.example.booksapi.domain.userregister.UserRegisterFacade;
import com.example.booksapi.domain.userregister.dto.RegisterRequestDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import com.example.booksapi.domain.userregister.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
class UserDetailsServiceImpl implements UserDetailsManager {

    private final UserRegisterFacade userRegisterFacade;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(final UserDetails user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        RegisterResponseDto responseDto = userRegisterFacade.register(
                RegisterRequestDto.builder()
                        .username(user.getUsername())
                        .password(encodedPassword)
                        .build()
        );
        log.info("User saved with id={}", responseDto.id());
    }

    @Override
    public void updateUser(final UserDetails user) {

    }

    @Override
    public void deleteUser(final String username) {

    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {

    }

    @Override
    public boolean userExists(final String username) {
        return userRegisterFacade.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDto userDto = userRegisterFacade.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                userDto.username(),
                userDto.password(),
                userDto.roles().stream()
                        .map(authority -> (GrantedAuthority) () -> authority)
                        .toList()
        );
    }

}
