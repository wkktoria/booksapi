package com.example.booksapi.domain.bookcrud;

import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.CreateBookRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BookCrudFacadeTest {

    BookRepository bookRepository = new InMemoryBookRepository();
    BookCrudFacade bookCrudFacade = new BookCrudFacade(
            new BookAdder(bookRepository, new BookRetriever(bookRepository))
    );

    @Test
    void should_create_new_book_when_title_is_unique() {
        // given
        String title = "Book";

        // when
        BookDto bookDto = bookCrudFacade.createBook(CreateBookRequestDto.builder()
                .title(title)
                .build());

        // then
        assertThat(bookDto.id()).isNotNull();
        assertThat(bookDto.title()).isEqualTo(title);
    }

    @Test
    void should_throw_exception_when_book_with_title_exists() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        bookCrudFacade.createBook(requestDto);

        // when
        Throwable throwable = catchThrowable(() -> bookCrudFacade.createBook(requestDto));

        // then
        assertThat(throwable).isInstanceOf(BookExistsException.class)
                .hasMessageContaining("exists");
    }

}