package com.learning.crud.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("author_details")
@Getter
@Setter
public class AuthorDetail {

    @Id
    private Long id;
    private Long authorId;
    private String bio;
}

