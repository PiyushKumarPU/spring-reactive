package com.learning.reactive.web.controller;

import com.learning.reactive.data.dto.UserResponse;
import com.learning.reactive.data.model.User;
import com.learning.reactive.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user by their UUID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public Mono<ResponseEntity<UserResponse>> get(@PathVariable UUID id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetches all users. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public Flux<User> getAll() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates a user by their UUID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public Mono<ResponseEntity<User>> update(@PathVariable UUID id, @RequestBody @Valid User user) {
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their UUID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return userService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

