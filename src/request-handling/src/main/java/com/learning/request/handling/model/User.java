package com.learning.request.handling.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("users")
@Getter
@Setter
public class User {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 0, message = "Age must be non-negative")
    private int age;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

}