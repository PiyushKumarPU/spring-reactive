package com.learning.crud.controller;

import com.learning.crud.entity.Book;
import com.learning.crud.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Flux<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/author/{authorId}")
    public Flux<Book> getBooksByAuthorId(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @PostMapping
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}
