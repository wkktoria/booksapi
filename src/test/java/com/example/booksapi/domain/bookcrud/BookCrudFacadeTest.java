package com.example.booksapi.domain.bookcrud;

import com.example.booksapi.domain.bookcrud.dto.AllBooksResponseDto;
import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.BookWithDetailsDto;
import com.example.booksapi.domain.bookcrud.dto.CreateBookRequestDto;
import com.example.booksapi.domain.bookcrud.dto.UpdateBookRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BookCrudFacadeTest {

    BookRepository bookRepository = new InMemoryBookRepository();
    BookRetriever bookRetriever = new BookRetriever(bookRepository);
    BookCrudFacade bookCrudFacade = new BookCrudFacade(
            new BookAdder(bookRepository, bookRetriever),
            bookRetriever,
            new BookUpdater(bookRetriever),
            new BookDeleter(bookRetriever, bookRepository),
            new BookInfoFetcherTestImpl()
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

    @Test
    void should_throw_exception_when_updating_to_existing_tile() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        bookCrudFacade.createBook(requestDto);

        // when
        Throwable throwable = catchThrowable(() -> bookCrudFacade.updateBook(1L, UpdateBookRequestDto.builder()
                .title(title)
                .build()));

        // then
        assertThat(throwable).isInstanceOf(BookExistsException.class)
                .hasMessageContaining("exists");
    }

    @Test
    void should_return_empty_set_when_no_books_saved() {
        // given

        // when
        AllBooksResponseDto responseDto = bookCrudFacade.findAllBooks(Pageable.unpaged());

        // then
        assertThat(responseDto.books().isEmpty()).isTrue();
    }

    @Test
    void should_return_set_of_books_when_there_are_saved_books() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        BookDto bookDto = bookCrudFacade.createBook(requestDto);

        // when
        AllBooksResponseDto responseDto = bookCrudFacade.findAllBooks(Pageable.unpaged());

        // then
        assertThat(responseDto.books().isEmpty()).isFalse();
        assertThat(responseDto.books().contains(bookDto)).isTrue();
    }

    @Test
    void should_throw_exception_when_book_not_found_by_id() {
        // given

        // when
        Throwable throwable = catchThrowable(() -> bookCrudFacade.findBookById(1L));

        // then
        assertThat(throwable).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void should_return_book_by_id_when_book_exists() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        BookDto bookDto = bookCrudFacade.createBook(requestDto);

        // when
        BookWithDetailsDto responseBookDto = bookCrudFacade.findBookById(bookDto.id());

        // then
        assertThat(responseBookDto).isNotNull();
        assertThat(responseBookDto.book().id()).isEqualTo(bookDto.id());
        assertThat(responseBookDto.book().title()).isEqualTo(bookDto.title());
    }

    @Test
    void should_update_book_title() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        BookDto bookDto = bookCrudFacade.createBook(requestDto);

        // when
        BookDto responseBookDto = bookCrudFacade.updateBook(bookDto.id(), UpdateBookRequestDto.builder()
                .title("Updated Book")
                .build());

        // then
        assertThat(responseBookDto).isNotNull();
        assertThat(responseBookDto.id()).isEqualTo(bookDto.id());
        assertThat(responseBookDto.title()).isEqualTo("Updated Book");
    }

    @Test
    void should_delete_book_by_id() {
        // given
        String title = "Book";
        CreateBookRequestDto requestDto = CreateBookRequestDto.builder()
                .title(title)
                .build();
        BookDto bookDto = bookCrudFacade.createBook(requestDto);

        // when
        bookCrudFacade.deleteBook(bookDto.id());

        // then
        assertThatThrownBy(() -> bookCrudFacade.findBookById(bookDto.id()))
                .isInstanceOf(BookNotFoundException.class);
    }

}