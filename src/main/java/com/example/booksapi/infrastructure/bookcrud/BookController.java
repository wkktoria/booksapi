package com.example.booksapi.infrastructure.bookcrud;

import com.example.booksapi.domain.bookcrud.BookCrudFacade;
import com.example.booksapi.domain.bookcrud.dto.AllBooksResponseDto;
import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.CreateBookRequestDto;
import com.example.booksapi.domain.bookcrud.dto.DeleteBookResponseDto;
import com.example.booksapi.domain.bookcrud.dto.UpdateBookRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Log4j2
class BookController {

    private final BookCrudFacade bookCrudFacade;

    @GetMapping
    ResponseEntity<AllBooksResponseDto> getAllBooks(@PageableDefault final Pageable pageable) {
        log.info("Received HTTP request to get all books: {}", pageable);
        AllBooksResponseDto responseDto = bookCrudFacade.findAllBooks(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable final Long id) {
        log.info("Received HTTP request to get book by id: {}", id);
        BookDto bookDto = bookCrudFacade.findBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    ResponseEntity<BookDto> createBook(@RequestBody @Valid final CreateBookRequestDto requestDto) {
        log.info("Received HTTP request to create book: {}", requestDto);
        BookDto bookDto = bookCrudFacade.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookDto> updateBook(@PathVariable final Long id,
                                       @RequestBody @Valid final UpdateBookRequestDto requestDto) {
        log.info("Received HTTP request to update book: {}", requestDto);
        BookDto bookDto = bookCrudFacade.updateBook(id, requestDto);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteBookResponseDto> deleteBook(@PathVariable final Long id) {
        log.info("Received HTTP request to delete book with id={}", id);
        DeleteBookResponseDto responseDto = bookCrudFacade.deleteBook(id);
        return ResponseEntity.ok(responseDto);
    }

}
