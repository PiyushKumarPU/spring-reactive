package com.learning.reactive.service;

import com.learning.reactive.common.exception.BusinessException;
import com.learning.reactive.common.exception.enums.ExceptionType;
import com.learning.reactive.data.dto.AuthenticationResponse;
import com.learning.reactive.data.dto.UserRequest;
import com.learning.reactive.data.dto.UserResponse;
import com.learning.reactive.data.mapper.UserMapper;
import com.learning.reactive.data.model.Role;
import com.learning.reactive.data.model.User;
import com.learning.reactive.data.model.UserRole;
import com.learning.reactive.data.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;
    private final JwtService jwtService;

    public Mono<UserResponse> register(UserRequest userRequest) {
        return validateRequest(userRequest)
                .thenMany(roleService.getByRoleNames(userRequest.getRoleNames()))
                .collectList()
                .flatMap(roles -> createUser(userRequest, roles))
                .flatMap(savedUser ->
                        assignRolesToUser(savedUser, userRequest.getRoleNames()) // save user_roles
                                .then(userService.findById(savedUser.getId())) // fetch full user
                );
    }

    public Mono<AuthenticationResponse> authenticate(String username, String password) {
        return reactiveAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .then(userService.findByUsername(username))
                .flatMap(this::createAuthResponse);
    }

    private Mono<AuthenticationResponse> createAuthResponse(User user) {
        return jwtService.generateAccessToken(user)
                .map(token -> {
                    String refreshToken = jwtService.generateRefreshToken(user);
                    return new AuthenticationResponse(
                            user.getId().toString(),
                            token,
                            refreshToken
                    );
                });
    }

    private Mono<Void> validateRequest(UserRequest request) {
        return authValidator.validateRegistrationPayload(request)
                .flatMap(errors -> {
                    if (!errors.isEmpty()) {
                        return Mono.error(BusinessException.builder(ExceptionType.INVALID_INPUT)
                                .validationErrors(errors)
                                .build());
                    }
                    return Mono.empty();
                });
    }

    private Mono<User> createUser(UserRequest request, List<Role> roles) {
        User user = UserMapper.INSTANCE.toUser(request, roles, passwordEncoder);
        return userService.save(user);
    }

    private Mono<Void> assignRolesToUser(User user, List<String> roleNames) {
        return roleService.getByRoleNames(roleNames)
                .flatMap(role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(role.getId());
                    return userRoleService.save(userRole);
                })
                .then();  // ignoring the return value of userRoleService.save(userRole);
    }
}
