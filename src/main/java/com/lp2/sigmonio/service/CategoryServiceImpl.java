package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(String categoryName)
            throws ResourceNotFoundException {
        Category category = findByName(categoryName);
        categoryRepository.delete(category);
        return "Deleted Successfully!";
    }

    @Override
    public ResponseEntity<Category> updateById(Category categoryDetails, long categoryId) {
        Category category = findById(categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        final Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ResponseEntity<Category> findOneById(long categoryId) {
        Category category = findById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @Override
    public Category findById(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category not found for this id :: "
                + categoryId));
    }

    @Override
    public List<Category> findByNameContains(String name) {
        return categoryRepository.findAllByNameContains(name);

    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Category not found for this name :: "
                + name));
    }

    @Override
    public boolean exists(String name) {
        return categoryRepository.existsByName(name);
    }
}
