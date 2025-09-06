package com.learning.request.handling.controller;

import com.learning.request.handling.model.User;
import com.learning.request.handling.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;

    // POST: Create User (Validates request body)
    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@Valid @RequestBody Mono<User> userMono) {
        return userMono
                .flatMap(user -> {
                    log.info("Creating user: {}", user.getEmail());
                    return userService.createUser(user);
                })
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser))
                .doOnError(e -> log.error("Error creating user", e));
    }

    // GET: Return all users (Flux demo)
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET: Return one user (Mono demo + ResponseEntity)
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Backpressure demo endpoint
     *
     * @param delayMillis - subscriber processing delay in milliseconds
     * @param bufferSize  - max buffer size for backpressure
     */
    @GetMapping(value = "/backpressure-demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getUsersWithBackpressure(
            @RequestParam(defaultValue = "500") long delayMillis,
            @RequestParam(defaultValue = "5") int bufferSize) {

        return userService.getAllUsers()
                .log()
                .delayElements(Duration.ofMillis(delayMillis))
                .onBackpressureBuffer(bufferSize)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(user -> log.info("Processed user: {}", user.getName()));
    }

    // GET: Handle query params (returns filtered by age)
    @GetMapping("/search")
    public Flux<User> searchByAge(@RequestParam int minAge) {
        return userService.findByAgeGreaterThan(minAge);
    }
}
