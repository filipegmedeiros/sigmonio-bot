package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    /**
     *
     * @param category object category
     * @return saved item
     */
    Category save(Category category);

    /**
     *
     * @param categoryName category name
     * @return if category has been deleted or not
     */
    String deleteCategory(String categoryName);

    /**
     *
     * @param category category object
     * @param id id of category
     * @return returns if is updated
     */
    ResponseEntity<Category> updateById(Category category, long id);

    /**
     *
     * @return list of all category in database
      */
    List<Category> findAll();

    /**
     *
     * @param id category id
     * @return only category with id specified
     */
    ResponseEntity<Category> findOneById(long id);

    /**
     *
     * @param id category id
     * @return only category with id specified
     */
    Category findById(long id);

    /**
     *
     * @param name category
     * @return list of category with partial name
     */
    List<Category> findByNameContains(String name);

    /**
     *
     * @param name category name
     * @return a object category with that name
     */
    Category findByName(String name);

    /**
     *
     * @param name category name
     * @return verify if exists or not
     */
    boolean exists(String name);
}
