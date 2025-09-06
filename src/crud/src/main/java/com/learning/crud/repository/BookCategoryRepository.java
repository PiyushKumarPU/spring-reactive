package com.learning.crud.repository;

import com.learning.crud.entity.BookCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookCategoryRepository extends ReactiveCrudRepository<BookCategory, Long> {
    Flux<BookCategory> findByBookId(Long bookId);
    Flux<BookCategory> findByCategoryId(Long categoryId);
}

