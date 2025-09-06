package com.learning.reactive.data.repository;

import com.learning.reactive.data.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, UUID> {

    Mono<Boolean> existsRoleByName(String name);

    Mono<Role> findByName(String name);

}
