package com.learning.request.handling.service;

import com.learning.request.handling.model.User;
import com.learning.request.handling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> findByAge(int age) {
        return userRepository.findByAge(age);
    }

    public Flux<User> findByAgeGreaterThan(int minAge) {
        return userRepository.findByAgeGreaterThan(minAge);
    }
}
