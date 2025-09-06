package com.learning.reactive.data.repository;

import com.learning.reactive.data.model.UserRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRole, UUID> {

    Flux<UserRole> findByUserId(UUID userId);

    Mono<Void> deleteByUserIdAndRoleId(UUID userId, UUID roleId);
}
