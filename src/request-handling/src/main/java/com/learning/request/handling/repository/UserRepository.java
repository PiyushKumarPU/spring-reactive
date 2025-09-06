package com.learning.request.handling.repository;

import com.learning.request.handling.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Flux<User> findByAge(int age);

    Flux<User> findByAgeGreaterThan(int minAge);
}
