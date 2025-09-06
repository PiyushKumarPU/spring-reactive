package com.learning.crud.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
@Getter
@Setter
public class Category {

    @Id
    private Long id;
    private String name;
}

