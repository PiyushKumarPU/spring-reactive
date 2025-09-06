package com.learning.crud.service;


import com.learning.crud.entity.Author;
import com.learning.crud.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Mono<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Mono<Author> createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Mono<Author> updateAuthor(Long id, Author author) {
        return authorRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(author.getName());
                    return authorRepository.save(existing);
                });
    }

    public Mono<Void> deleteAuthor(Long id) {
        return authorRepository.deleteById(id);
    }
}

