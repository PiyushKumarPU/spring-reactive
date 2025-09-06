package com.learning.users.controller;

import com.learning.users.model.User;
import com.learning.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
