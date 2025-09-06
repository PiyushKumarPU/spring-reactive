package com.learning.crud.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("book_categories")
@Getter
@Setter
public class BookCategory {

    @Id
    private Long id;
    private Long bookId;
    private Long categoryId;
}

