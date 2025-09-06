package com.learning.reactive.common.exception.handler;

import com.learning.reactive.common.api.response.APIResponse;
import com.learning.reactive.common.exception.BusinessException;
import com.learning.reactive.common.exception.enums.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<APIResponse<?>> handleGlobalException(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        ExceptionType exceptionType = exception.getExceptionType();
        HttpStatusCode statusCode =
                exceptionType.getStatus() != null ? exceptionType.getStatus() : HttpStatus.BAD_REQUEST;
        APIResponse<?> result =
                exception.getApiResponse() != null ? exception.getApiResponse() : new APIResponse<>();

        if (exception.getValidationErrors() != null && !exception.getValidationErrors().isEmpty()) {
            statusCode = HttpStatus.BAD_REQUEST;
            result = APIResponse.failure(exception.getValidationErrors(), exceptionType.getDescription());
        }
        return ResponseEntity.status(statusCode).body(result);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    protected ResponseEntity<APIResponse<?>> handleAuthenticationException(RuntimeException ex) {
        log.error("Authentication failed: {}", ex.getMessage(), ex);

        APIResponse<?> response = APIResponse.failure(
                HttpStatus.UNAUTHORIZED.name(),
                "Invalid username or password" // generic message
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    protected ResponseEntity<APIResponse<?>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.error("Authorization failed: {}", ex.getMessage(), ex);

        APIResponse<?> response = APIResponse.failure(
                HttpStatus.FORBIDDEN.name(),
                "You do not have permission to access this resource"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<APIResponse<?>> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest()
                .body(APIResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage()));
    }
}
