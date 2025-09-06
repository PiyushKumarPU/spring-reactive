package com.learning.reactive.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "roles")
@Data
public class Role {

    @Id
    private UUID id;

    @Column("name")
    private String name;

    @Column
    private String description;

    @Column
    @Version
    @ReadOnlyProperty
    private Long version;

    @CreatedDate
    @JsonIgnore
    @Column("created_at")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column("created_by")
    private String createdBy;
}
