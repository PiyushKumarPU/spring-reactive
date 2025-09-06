package com.learning.reactive.data.repository;

import com.learning.reactive.data.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByMobile(String mobile);
}