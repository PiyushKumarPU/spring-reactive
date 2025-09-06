package com.learning.reactive.service;

import com.learning.reactive.data.model.Role;
import com.learning.reactive.data.repository.RoleRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class RoleService {

    private final RoleRepository roleRepository;

    public Flux<Role> getByRoleNames(
            @NotNull(message = "Roles are required")
            @Size(min = 1, message = "At least one role must be specified")
            List<String> roles) {

        return Flux.fromIterable(roles)
                .flatMap(this::findByName); // findByName returns Mono<Role>
    }

    Mono<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Mono<Role> findById(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    /*


    public Mono<Role> create(@Valid Role role) {
        return roleRepository.save(role);
    }

    public Mono<Role> findById(UUID id) {
        return roleRepository.findById(id);
    }

    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

    // Update role
    public Mono<Role> update(UUID id, Role role) {
        return roleRepository.findById(id)
                .flatMap(existingRole -> {
                    existingRole.setName(role.getName());
                    return roleRepository.save(existingRole);
                });
    }

    // Delete role
    public Mono<Void> delete(UUID id) {
        return roleRepository.deleteById(id);
    }

    public Mono<Boolean> existsByName(String roleName) {
        return roleRepository.existsRoleByName(roleName);
    }*/
}
