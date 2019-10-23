package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category save(Category category);
    Map<String, Boolean> deleteCategory(Long categoryId);
    ResponseEntity<Category> updateById(Category category, long id);
    List<Category> findAll();
    ResponseEntity<Category> findOneById(long id);
    Category findById(long id);

    Category findByName(String name);
}
