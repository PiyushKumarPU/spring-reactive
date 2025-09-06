package com.learning.crud.repository;

import com.learning.crud.entity.AuthorDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorDetailRepository extends ReactiveCrudRepository<AuthorDetail, Long> {
    Mono<AuthorDetail> findByAuthorId(Long authorId);
}

