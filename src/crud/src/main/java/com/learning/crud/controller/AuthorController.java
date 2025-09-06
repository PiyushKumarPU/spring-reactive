package com.learning.crud.controller;

import com.learning.crud.entity.Author;
import com.learning.crud.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Flux<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Author>> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Author>> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}

