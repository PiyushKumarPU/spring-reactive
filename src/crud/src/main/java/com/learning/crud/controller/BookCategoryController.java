package com.learning.crud.controller;

import com.learning.crud.entity.BookCategory;
import com.learning.crud.service.BookCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book-categories")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping
    public Flux<BookCategory> getAllBookCategories() {
        return bookCategoryService.getAllBookCategories();
    }

    @GetMapping("/book/{bookId}")
    public Flux<BookCategory> getByBookId(@PathVariable Long bookId) {
        return bookCategoryService.getByBookId(bookId);
    }

    @GetMapping("/category/{categoryId}")
    public Flux<BookCategory> getByCategoryId(@PathVariable Long categoryId) {
        return bookCategoryService.getByCategoryId(categoryId);
    }

    @PostMapping
    public Mono<BookCategory> createBookCategory(@RequestBody BookCategory bookCategory) {
        return bookCategoryService.createBookCategory(bookCategory);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBookCategory(@PathVariable Long id) {
        return bookCategoryService.deleteBookCategory(id)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}

