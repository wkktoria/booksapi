package com.example.booksapi.domain.userregister;

import com.example.booksapi.domain.userregister.dto.RegisterRequestDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import com.example.booksapi.domain.userregister.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserRegisterFacade {

    public static final String DEFAULT_USER_ROLE = "ROLE_USER";

    public static final String USER_EXISTS = "User already exists";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    public static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;

    public RegisterResponseDto register(final RegisterRequestDto requestDto) {
        final String username = requestDto.username();

        if (userRepository.existsByUsername(username)) {
            log.warn("Could not register user - user with username='{}' already exists", username);
            throw new UserExistsException(USER_EXISTS);
        }

        final User user = User.builder()
                .username(requestDto.username())
                .password(requestDto.password())
                .authorities(List.of(DEFAULT_USER_ROLE))
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);

        return RegisterResponseDto.builder()
                .message(USER_REGISTERED_SUCCESSFULLY)
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDto findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getAuthorities())
                        .enabled(user.isEnabled())
                        .build())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

}
