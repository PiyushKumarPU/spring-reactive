package com.learning.reactive.service;

import com.learning.reactive.data.dto.AuthenticationResponse;
import com.learning.reactive.data.model.User;
import com.learning.reactive.props.TokenProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtService {

    /*@Value("${app.token.secret}")
    private String secretKey;

    @Value("${app.token.accessTokenExpirySeconds}")
    private Long accessTokenExpirySeconds;

    @Value("${app.token.refreshTokenExpirySeconds}")
    private Long refreshTokenExpirySeconds;*/

    private final UserService userService;
    private final TokenProperties tokenProperties;

    public Mono<String> generateAccessToken(User user) {
        return buildClaims(user)   // returns Mono<Map<String, Object>>
                .map(claims ->
                        constructToken(
                                user.getUsername(),
                                claims,
                                Date.from(Instant.now().plus(tokenProperties.getAccessTokenExpirySeconds(), ChronoUnit.SECONDS))
                        )
                );
    }

    public String generateRefreshToken(User user) {
        return constructToken(user.getUsername(), Map.of(), Date.from(Instant.now().plus(tokenProperties.getRefreshTokenExpirySeconds(), ChronoUnit.SECONDS)));
    }

    public Mono<Boolean> isValidJwt(String token) {
        return Mono.just(token)
                .map(this::parseToken)
                .map(claims -> !claims.getExpiration().before(new Date()))
                .onErrorReturn(false);
    }

    public String getSubjectFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public Mono<List<String>> getRolesFromToken(String token) {
        return Mono.fromSupplier(() -> {
            Claims claims = parseToken(token);
            Object rolesObj = claims.get("roles");

            if (rolesObj instanceof List<?>) {
                return ((List<?>) rolesObj).stream()
                        .map(Object::toString)
                        .toList();
            }

            return Collections.emptyList();
        });
    }

    public Mono<AuthenticationResponse> generateAuthResponse(String refreshToken) {
        return isValidJwt(refreshToken) // Mono<Boolean>
                .flatMap(isValid -> {
                    if (isValid) {
                        // Extract subject (username) from the refresh token
                        String subject = getSubjectFromToken(refreshToken);

                        // Find user reactively
                        return userService.findByUsername(subject)
                                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + subject)))
                                .flatMap(user ->
                                        generateAccessToken(user) // Mono<String>
                                                .map(accessToken -> {
                                                    String newRefreshToken = generateRefreshToken(user);
                                                    return new AuthenticationResponse(
                                                            user.getId().toString(),
                                                            accessToken,
                                                            newRefreshToken
                                                    );
                                                })
                                );
                    } else {
                        return Mono.error(new BadCredentialsException("Invalid or expired refresh token"));
                    }
                });
    }


    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String constructToken(String subject, Map<String, Object> claims, Date expiration) {
        return Jwts
                .builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Optional.ofNullable(tokenProperties.getSecret())
                .map(String::getBytes)
                .map(Keys::hmacShaKeyFor)
                .orElseThrow(() -> new IllegalArgumentException("token.secret must be configured in the application.properties file"));
    }

    private Mono<Map<String, Object>> buildClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        // Core identity
        claims.put("userId", user.getId().toString());
        claims.put("username", user.getUsername());
        // Account status
        claims.put("accountStatus", user.getAccountStatus().name());

        // Add roles reactively
        return userService.getRoleNamesForUser(user.getId())
                .map(roleNames -> {
                    claims.put("roles", roleNames);
                    return claims;
                });
    }
}
