package com.learning.reactive.service;

import com.learning.reactive.data.model.AccountStatusEnum;
import com.learning.reactive.data.model.User;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public record ReactiveUserDetailsServiceImpl(UserService userService) implements ReactiveUserDetailsService {

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(
                        "User not found with username: " + username)))
                .flatMap(this::validateUser)
                .flatMap(user -> userService.getRoleNamesForUser(user.getId())
                        .map(roles -> mapToUserDetails(user, roles)));
    }


    // Helper method
    private UserDetails mapToUserDetails(User user, List<String> roles) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        roles.toArray(String[]::new)
                )
                .accountLocked(user.isAccountLocked())
                .accountExpired(user.isAccountExpired())
                .credentialsExpired(user.isCredentialsExpired())
                .disabled(user.getAccountStatus() != AccountStatusEnum.ACTIVE)
                .build();
    }

    private Mono<User> validateUser(User user) {
        if (user.getAccountStatus() != AccountStatusEnum.ACTIVE) {
            return Mono.error(new DisabledException("User account is not active"));
        }
        if (user.isAccountLocked()) {
            return Mono.error(new LockedException("User account is locked"));
        }
        if (user.isAccountExpired()) {
            return Mono.error(new AccountExpiredException("User account has expired"));
        }
        if (user.isCredentialsExpired()) {
            return Mono.error(new CredentialsExpiredException("User credentials have expired"));
        }
        return Mono.just(user);
    }
}


