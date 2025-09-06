package com.learning.crud.service;

import com.learning.crud.entity.BookCategory;
import com.learning.crud.repository.BookCategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    public BookCategoryService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public Flux<BookCategory> getAllBookCategories() {
        return bookCategoryRepository.findAll();
    }

    public Flux<BookCategory> getByBookId(Long bookId) {
        return bookCategoryRepository.findByBookId(bookId);
    }

    public Flux<BookCategory> getByCategoryId(Long categoryId) {
        return bookCategoryRepository.findByCategoryId(categoryId);
    }

    public Mono<BookCategory> createBookCategory(BookCategory bookCategory) {
        return bookCategoryRepository.save(bookCategory);
    }

    public Mono<Void> deleteBookCategory(Long id) {
        return bookCategoryRepository.deleteById(id);
    }
}

