package com.learning.reactive.data.mapper;

import com.learning.reactive.data.dto.UserRequest;
import com.learning.reactive.data.dto.UserResponse;
import com.learning.reactive.data.model.Role;
import com.learning.reactive.data.model.RoleEnum;
import com.learning.reactive.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "accountLocked", constant = "false")
    @Mapping(target = "accountExpired", constant = "false")
    @Mapping(target = "credentialsExpired", constant = "false")
    @Mapping(target = "passwordExpired", constant = "false")
    @Mapping(target = "failedAttempts", constant = "0")
    @Mapping(target = "lastFailedAttempt", ignore = true)
    @Mapping(target = "lastPasswordChange", ignore = true)
    User toUser(UserRequest userRequest, List<Role> roles, PasswordEncoder passwordEncoder);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toUserResponse(User user, List<Role> roles);

    default LocalDateTime map(OffsetDateTime value) {
        return value != null ? value.toLocalDateTime() : null;
    }

    // This helper method is automatically picked up by MapStruct
    default List<RoleEnum> mapRoles(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> RoleEnum.valueOf(role.getName()))
                .collect(Collectors.toList());
    }
}

