package com.learning.crud.service;

import com.learning.crud.entity.Category;
import com.learning.crud.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Mono<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Mono<Category> createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Mono<Category> updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(category.getName());
                    return categoryRepository.save(existing);
                });
    }

    public Mono<Void> deleteCategory(Long id) {
        return categoryRepository.deleteById(id);
    }
}

