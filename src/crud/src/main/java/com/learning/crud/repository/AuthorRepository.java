package com.learning.crud.repository;


import com.learning.crud.entity.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}

