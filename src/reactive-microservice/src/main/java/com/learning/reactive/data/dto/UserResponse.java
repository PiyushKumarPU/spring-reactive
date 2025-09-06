package com.learning.reactive.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.reactive.data.model.AccountStatusEnum;
import com.learning.reactive.data.model.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
//@Builder
@Schema(description = "User details model")
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "Unique identifier for the user")
    private UUID id;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Username of the user")
    private String username;

    @Schema(description = "Password of the user")
    @JsonIgnore
    private String password;

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "Mobile number of the user")
    private String mobile;

    @Schema(description = "Account status of the user")
    private AccountStatusEnum accountStatus;

    @Schema(description = "Indicates if the account is locked")
    @JsonIgnore
    private boolean accountLocked;

    @Schema(description = "Indicates if the account is expired")
    @JsonIgnore
    private boolean accountExpired;

    @Schema(description = "Indicates if the credentials are expired")
    @JsonIgnore
    private boolean credentialsExpired;

    @Schema(description = "Indicates if the password is expired")
    @JsonIgnore
    private boolean passwordExpired;

    @Schema(description = "Number of failed login attempts")
    @JsonIgnore
    private int failedAttempts;

    @Schema(description = "Timestamp of the last failed login attempt")
    @JsonIgnore
    private LocalDateTime lastFailedAttempt;

    @Schema(description = "Timestamp of the last password change")
    @JsonIgnore
    private LocalDateTime lastPasswordChange;

    @Schema(description = "List of roles assigned to the user")
    private List<RoleEnum> roles;

    @Schema(description = "Version of the user record")
    private Long version;

    @Schema(description = "Timestamp when the user was created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "User who created this record")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;

    @Schema(description = "Timestamp when the user was last updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Schema(description = "User who last updated this record")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;


}
