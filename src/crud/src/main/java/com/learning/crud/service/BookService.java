package com.learning.crud.service;

import com.learning.crud.entity.Book;
import com.learning.crud.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Book> updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .flatMap(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthorId(book.getAuthorId());
                    return bookRepository.save(existing);
                });
    }

    public Mono<Void> deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
}
