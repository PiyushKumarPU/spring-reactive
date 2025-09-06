package com.learning.reactive.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String mobile;

    @Column
    private AccountStatusEnum accountStatus = AccountStatusEnum.ACTIVE;

    @Column("account_locked")
    private boolean accountLocked = false;

    @Column("account_expired")
    private boolean accountExpired = false;

    @Column("credentials_expired")
    private boolean credentialsExpired = false;

    @Column("password_expired")
    private boolean passwordExpired = false;

    @Column("failed_attempts")
    private int failedAttempts = 0;

    @Column("last_failed_attempt")
    private LocalDateTime lastFailedAttempt;

    @Column("last_password_change")
    private LocalDateTime lastPasswordChange;

    @Transient
    private List<Role> roles;

    @Version
    @ReadOnlyProperty
    @Column("version")
    private Long version;

    @CreatedBy
    @Column("created_by")
    private String createdBy;

    @CreatedDate
    @JsonIgnore
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column("updated_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    @Column("updated_at")
    private LocalDateTime updatedAt;
}