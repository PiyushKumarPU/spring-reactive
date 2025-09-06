package com.learning.request.handling.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle @Valid validation errors
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationException(WebExchangeBindException ex, ServerWebExchange exchange) {

        // Collect all field errors
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    assert error.getDefaultMessage() != null;
                    return Map.of(
                            "field", error.getField(),
                            "message", error.getDefaultMessage()
                    );
                })
                .collect(Collectors.toList());

        // Prepare structured response
        Map<String, Object> errorResponse = Map.of(
                "message", "Validation failed for one or more fields",
                "status", HttpStatus.BAD_REQUEST.value(),
                "timestamp", LocalDateTime.now(),
                "path", exchange.getRequest().getPath().value(),
                "errors", fieldErrors
        );

        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    // Handle duplicate key (e.g., unique email)
    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleDuplicateKey(DuplicateKeyException ex) {
        Map<String, String> error = new HashMap<>();

        String message = ex.getMessage() != null ? ex.getMessage() : "";
        String fieldName = "field"; // default fallback

        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\((.*?)\\)").matcher(message);
        if (matcher.find()) {
            fieldName = matcher.group(1).toLowerCase(); // extract field name
        }

        error.put("error", capitalize(fieldName) + " already exists. Please use a different " + fieldName + ".");

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error));
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "An unexpected error occurred. Please try again later.");
        error.put("errorType", ex.getClass().getSimpleName());
        error.put("timestamp", java.time.LocalDateTime.now());
        error.put("path", exchange.getRequest().getPath().value());

        log.error(ex.getMessage(), ex); // log stack trace
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }

    // Helper method to capitalize first letter
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
