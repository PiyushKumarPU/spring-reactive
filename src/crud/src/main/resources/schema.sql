CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE author_details (
    id SERIAL PRIMARY KEY,
    author_id BIGINT UNIQUE NOT NULL,
    bio TEXT,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT fk_author_book FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_categories (
     id SERIAL PRIMARY KEY,
     book_id BIGINT NOT NULL,
     category_id BIGINT NOT NULL,
     CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
     CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
     CONSTRAINT uc_book_category UNIQUE (book_id, category_id)
);
