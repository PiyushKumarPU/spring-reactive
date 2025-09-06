package com.learning.crud.repository;

import com.learning.crud.entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    Flux<Book> findByAuthorId(Long authorId);
}

