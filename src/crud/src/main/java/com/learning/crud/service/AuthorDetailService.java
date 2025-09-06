package com.learning.crud.service;

import com.learning.crud.entity.AuthorDetail;
import com.learning.crud.repository.AuthorDetailRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorDetailService {

    private final AuthorDetailRepository detailRepository;

    public AuthorDetailService(AuthorDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public Mono<AuthorDetail> getDetailByAuthorId(Long authorId) {
        return detailRepository.findByAuthorId(authorId);
    }

    public Mono<AuthorDetail> createOrUpdateDetail(AuthorDetail detail) {
        // Save works as create or update depending on ID presence
        return detailRepository.save(detail);
    }

    public Mono<Void> deleteDetail(Long id) {
        return detailRepository.deleteById(id);
    }
}

