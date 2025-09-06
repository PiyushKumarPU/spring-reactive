package com.learning.crud.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
@Getter
@Setter
public class Book {

    @Id
    private Long id;
    private String title;
    private Long authorId; // Foreign key to Author


}

