package com.learning.reactive.web.controller;

import com.learning.reactive.common.api.response.APIResponse;
import com.learning.reactive.data.dto.AuthenticationRequest;
import com.learning.reactive.data.dto.AuthenticationResponse;
import com.learning.reactive.data.dto.RefreshTokenRequest;
import com.learning.reactive.data.dto.UserRequest;
import com.learning.reactive.data.dto.UserResponse;
import com.learning.reactive.service.AuthService;
import com.learning.reactive.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows a new user to register in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Mono<APIResponse<UserResponse>> register(@Valid @RequestBody UserRequest userRequest) {
        return authService.register(userRequest)
                .map(userResponse ->
                        APIResponse.success(userResponse, "User registered successfully")
                );
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a user",
            description = "This endpoint allows a user to log in and receive authentication tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Mono<APIResponse<AuthenticationResponse>> login(
            @Valid @RequestBody Mono<AuthenticationRequest> authenticationRequestMono) {

        return authenticationRequestMono
                .flatMap(authRequest ->
                        authService.authenticate(authRequest.getUsername(), authRequest.getPassword())
                                .map(authResponse ->
                                        APIResponse.success(authResponse, "User logged in successfully")
                                )
                );
    }


    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh authentication token",
            description = "This endpoint allows a user to refresh their authentication token using a valid refresh token. No Authorization header required."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<APIResponse<?>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();

        return jwtService.isValidJwt(refreshToken)  // Mono<Boolean>
                .flatMap(isValid -> {
                    if (isValid) {
                        return jwtService.generateAuthResponse(refreshToken)
                                .map(authResponse ->
                                        APIResponse.success(authResponse, "Token generated successfully.")
                                );
                    } else {
                        return Mono.just(
                                APIResponse.failure(
                                        String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                                        "Invalid or expired refresh token"
                                )
                        );
                    }
                });
    }

    @GetMapping("/validate-token")
    @Operation(
            summary = "Validate JWT token",
            description = "Validates the provided JWT token in the Authorization header"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Invalid token")
    })
    public Mono<ResponseEntity<String>> validateToken(
            @Parameter(
                    description = "JWT token in the format 'Bearer {token}'",
                    required = true,
                    in = ParameterIn.HEADER,
                    name = "Authorization"
            )
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "").trim();
        log.info("Started validate token: {}", token);

        return jwtService.isValidJwt(token) // Mono<Boolean>
                .map(isValid -> {
                    if (isValid) {
                        return ResponseEntity.ok("Token is valid");
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                    }
                });
    }
}
