package com.learning.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

@Configuration
@EnableR2dbcAuditing
public class R2dbcConfig {

    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return () -> ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    if (securityContext.getAuthentication() != null &&
                            securityContext.getAuthentication().isAuthenticated() &&
                            !"anonymousUser".equals(securityContext.getAuthentication().getPrincipal())) {
                        return securityContext.getAuthentication().getName();
                    } else {
                        return "System";
                    }
                })
                .defaultIfEmpty("System");
    }

}
