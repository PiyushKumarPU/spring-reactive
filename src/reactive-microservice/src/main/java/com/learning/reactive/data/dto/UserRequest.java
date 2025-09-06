package com.learning.reactive.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.learning.reactive.data.model.AccountStatusEnum;
import com.learning.reactive.data.model.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({
        "firstName",
        "lastName",
        "username",
        "password",
        "email",
        "mobile",
        "accountStatus",
        "roles"
})
public class UserRequest {

    @NotNull(message = "First Name is required")
    @Size(min = 3, max = 50, message = "First Name can not be greater than 50 character.")
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @NotNull(message = "Username is required")
    @Schema(description = "User's Username", example = "Ramesh123")
    private String username;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(
            description = "User's password. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit.",
            example = "Password123"
    )
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "Account status is required")
    @Schema(description = "The status of the account. Must be one of the following: ACTIVE, INACTIVE, SUSPENDED.")
    private AccountStatusEnum accountStatus;

    @NotNull(message = "Roles are required")
    @Size(min = 1, message = "At least one role must be specified")
    @Schema(description = "The role(s) of account. Must be one or more of the following: ROLE_USER, ROLE_ADMIN, ROLE_SALES, ROLE_OPERATIONS.")
    private List<RoleEnum> roles;

    @JsonIgnore
    public List<String> getRoleNames(){
        return this.roles.stream().map(RoleEnum::getName).toList();
    }

}
