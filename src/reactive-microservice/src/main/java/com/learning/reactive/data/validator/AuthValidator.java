package com.learning.reactive.data.validator;

import com.learning.reactive.common.api.response.error.FieldValidationError;
import com.learning.reactive.data.dto.UserRequest;
import com.learning.reactive.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final UserRepository userRepository;

    public Mono<List<FieldValidationError>> validateRegistrationPayload(UserRequest userRequest) {
        return Flux.concat(
                        userRepository.existsByUsername(userRequest.getUsername())
                                .filter(Boolean::booleanValue)
                                .map(exists -> new FieldValidationError("username", userRequest.getUsername(), "Username already taken.")),

                        userRepository.existsByEmail(userRequest.getEmail())
                                .filter(Boolean::booleanValue)
                                .map(exists -> new FieldValidationError("email", userRequest.getEmail(), "Email already taken.")),

                        userRepository.existsByMobile(userRequest.getMobile())
                                .filter(Boolean::booleanValue)
                                .map(exists -> new FieldValidationError("mobile", userRequest.getMobile(), "Mobile number already taken.")),

                        Mono.just(userRequest.getPassword())
                                .filter(password -> !isValidPassword(password))
                                .map(password -> new FieldValidationError("password", password,
                                        "User's password. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit."))
                )
                .collectList();
    }

    private Boolean isValidPassword(String password) {
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasMinimumLength = password.length() >= 8;
        return hasUppercase && hasLowercase && hasDigit && hasMinimumLength;
    }
}

