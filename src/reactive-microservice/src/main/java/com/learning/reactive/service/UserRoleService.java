package com.learning.reactive.service;

import com.learning.reactive.data.model.Role;
import com.learning.reactive.data.model.UserRole;
import com.learning.reactive.data.repository.UserRepository;
import com.learning.reactive.data.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    Mono<UserRole> save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    Flux<UserRole> save(List<UserRole> userRoles) {
        return userRoleRepository.saveAll(userRoles);
    }

    public Flux<UserRole> findAllByUserId(UUID id) {
        return userRoleRepository.findByUserId(id);
    }


    /*// Assign role to user
    public Mono<Void> assignRole(UUID userId, UUID roleId) {
        UserRole userRole = new UserRole(UUID.randomUUID(), userId, roleId);
        return userRoleRepository.save(userRole).then();
    }

    // Remove role from user
    public Mono<Void> removeRole(UUID userId, UUID roleId) {
        return userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }

    // Get all roles of a user
    public Flux<Role> getRolesForUser(UUID userId) {
        return userRoleRepository.findAllByUserId(userId)
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()));
    }*/
}

