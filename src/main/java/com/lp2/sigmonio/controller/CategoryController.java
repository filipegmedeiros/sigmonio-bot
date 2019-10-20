package com.lp2.sigmonio.controller;

import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> getAllCategorys() {
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId){
        return categoryService.findOneById(categoryId);
    }

    @PostMapping("/category")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId,
                                                           @Valid @RequestBody Category categoryDetails){

        return categoryService.updateById(categoryDetails, categoryId);
    }

    @DeleteMapping("/category/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}