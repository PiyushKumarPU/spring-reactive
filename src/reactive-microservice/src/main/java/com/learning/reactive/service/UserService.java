package com.learning.reactive.service;

import com.learning.reactive.data.dto.UserResponse;
import com.learning.reactive.data.mapper.UserMapper;
import com.learning.reactive.data.model.Role;
import com.learning.reactive.data.model.User;
import com.learning.reactive.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }




    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> update(UUID id, User updatedUser) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    user.setMobile(updatedUser.getMobile());
                    user.setAccountStatus(updatedUser.getAccountStatus());
                    user.setAccountLocked(updatedUser.isAccountLocked());
                    user.setAccountExpired(updatedUser.isAccountExpired());
                    user.setCredentialsExpired(updatedUser.isCredentialsExpired());
                    user.setPasswordExpired(updatedUser.isPasswordExpired());
                    user.setFailedAttempts(updatedUser.getFailedAttempts());
                    user.setLastFailedAttempt(updatedUser.getLastFailedAttempt());
                    user.setLastPasswordChange(updatedUser.getLastPasswordChange());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<Void> deleteById(UUID id) {
        return userRepository.deleteById(id);
    }

    public Mono<UserResponse> findById(UUID id) {
        return userRepository.findById(id)
                .flatMap(user ->
                        getRolesForUser(user.getId())
                                .map(roles -> userMapper.toUserResponse(user, roles))
                );
    }


    public Mono<List<Role>> getRolesForUser(UUID userId) {
        return userRoleService.findAllByUserId(userId)       // Flux<UserRole>
                .flatMap(userRole -> roleService.findById(userRole.getRoleId())) // Flux<Role>
                .collectList(); // Mono<List<Role>>
    }


    public Mono<List<String>> getRoleNamesForUser(UUID userId) {
        return userRoleService.findAllByUserId(userId)       // Flux<UserRole>
                .flatMap(userRole -> roleService.findById(userRole.getRoleId())) // Flux<Role>
                .map(Role::getName)
                .collectList(); // Mono<List<Role>>
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    /*private final UserRepository userRepository;

    public Mono<User> create(User user) {
        return userRepository.existsByUsername(user.getUsername())
                .flatMap(usernameExists -> {
                    if (usernameExists) {
                        return Mono.error(new DuplicateFieldException("username", "Username already exists"));
                    }
                    return userRepository.existsByEmail(user.getEmail());
                })
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(new DuplicateFieldException("email", "Email already exists"));
                    }
                    return userRepository.save(user);
                });
    }


   */
}
