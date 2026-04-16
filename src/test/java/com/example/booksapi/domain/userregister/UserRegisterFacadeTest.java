package com.example.booksapi.domain.userregister;

import com.example.booksapi.domain.userregister.dto.RegisterRequestDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import com.example.booksapi.domain.userregister.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.booksapi.domain.userregister.UserRegisterFacade.DEFAULT_USER_ROLE;
import static com.example.booksapi.domain.userregister.UserRegisterFacade.USER_EXISTS;
import static com.example.booksapi.domain.userregister.UserRegisterFacade.USER_NOT_FOUND;
import static com.example.booksapi.domain.userregister.UserRegisterFacade.USER_REGISTERED_SUCCESSFULLY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class UserRegisterFacadeTest {

    UserRegisterFacade userRegisterFacade = new UserRegisterFacade(new InMemoryUserRepository());

    @Test
    void should_register_user() {
        // given
        String username = "user";
        String password = "pass123";
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        // when
        RegisterResponseDto responseDto = userRegisterFacade.register(requestDto);

        // then
        assertThat(responseDto.username()).isEqualTo(username);
        assertThat(responseDto.message()).isEqualTo(USER_REGISTERED_SUCCESSFULLY);
    }

    @Test
    void should_register_user_with_default_role() {
        // given
        String username = "user";
        String password = "pass123";
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        // when
        RegisterResponseDto responseDto = userRegisterFacade.register(requestDto);

        // then
        UserDto userDto = userRegisterFacade.findByUsername(responseDto.username());
        assertThat(userDto.roles()).isEqualTo(List.of(DEFAULT_USER_ROLE));
    }

    @Test
    void should_throw_exception_when_user_exists() {
        // given
        String username = "user";
        String password = "pass123";
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();
        userRegisterFacade.register(requestDto);

        // when
        Throwable throwable = catchThrowable(() -> userRegisterFacade.register(requestDto));

        // then
        assertThat(throwable).isInstanceOf(UserExistsException.class)
                .hasMessage(USER_EXISTS);
    }

    @Test
    void should_return_true_when_user_saved() {
        // given
        String username = "user";
        String password = "pass123";
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();
        userRegisterFacade.register(requestDto);

        // when
        boolean userExists = userRegisterFacade.existsByUsername(username);

        // then
        assertThat(userExists).isTrue();
    }

    @Test
    void should_return_false_when_user_not_saved() {
        // given
        String username = "user";

        // when
        boolean userExists = userRegisterFacade.existsByUsername(username);

        // then
        assertThat(userExists).isFalse();
    }

    @Test
    void should_find_user_by_username() {
        // given
        String username = "user";
        String password = "pass123";
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();
        userRegisterFacade.register(requestDto);

        // when
        UserDto userDto = userRegisterFacade.findByUsername(username);

        // then
        assertThat(userDto.username()).isEqualTo(username);
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        // given
        String username = "user";

        // when
        Throwable throwable = catchThrowable(() -> userRegisterFacade.findByUsername(username));

        // then
        assertThat(throwable).isInstanceOf(UserNotFoundException.class)
                .hasMessage(USER_NOT_FOUND);
    }

}