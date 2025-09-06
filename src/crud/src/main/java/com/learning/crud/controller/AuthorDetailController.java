package com.learning.crud.controller;

import com.learning.crud.entity.AuthorDetail;
import com.learning.crud.service.AuthorDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/author-details")
public class AuthorDetailController {

    private final AuthorDetailService authorDetailService;

    public AuthorDetailController(AuthorDetailService authorDetailService) {
        this.authorDetailService = authorDetailService;
    }

    @GetMapping("/author/{authorId}")
    public Mono<ResponseEntity<AuthorDetail>> getDetailByAuthorId(@PathVariable Long authorId) {
        return authorDetailService.getDetailByAuthorId(authorId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<AuthorDetail> createOrUpdateDetail(@RequestBody AuthorDetail detail) {
        return authorDetailService.createOrUpdateDetail(detail);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDetail(@PathVariable Long id) {
        return authorDetailService.deleteDetail(id)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}

