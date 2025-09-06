package com.learning.reactive.web.filter;

import com.learning.reactive.constant.AppConstant;
import com.learning.reactive.service.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    /**
     * @param exchange – the current server exchange
     * @param chain – provides a way to delegate to the next filter
     * @Returns: Mono<Void> to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,@NonNull WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (isPublicUrl(path)) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);
        if (token == null) return chain.filter(exchange);
        return validateToken(token)
                .flatMap(isValid -> isValid ? authenticateAndContinue(token, exchange, chain)
                        : handleInvalidToken(exchange));
    }

    private Mono<Void> authenticateAndContinue(String token, ServerWebExchange exchange, WebFilterChain chain) {
        return jwtService.getRolesFromToken(token)
                .flatMap(roles -> {
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            jwtService.getSubjectFromToken(token),
                            null,
                            authorities
                    );
                    log.info("authorities: {}", authorities);
                    return chain
                            .filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                });
    }


    private Mono<Void> handleInvalidToken(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private String extractToken(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        return null;
    }

    private Mono<Boolean> validateToken(String token) {
        return jwtService.isValidJwt(token);
    }

    private boolean isPublicUrl(String path) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : AppConstant.PERMIT_ALL_URLS) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}
