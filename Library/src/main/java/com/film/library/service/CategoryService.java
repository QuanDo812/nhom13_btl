package com.film.library.service;

import com.film.library.dto.CategoryDTO;
import com.film.library.model.Category;

import java.util.List;

public interface CategoryService {
    /*Admin*/
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);
    List<Category> findAllByActivated();

    /*Customer*/
    List<CategoryDTO> getCategoryDTO();
}
