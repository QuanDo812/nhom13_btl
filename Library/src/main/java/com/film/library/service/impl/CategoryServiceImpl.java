package com.film.library.service.impl;

import com.film.library.dto.CategoryDTO;
import com.film.library.model.Category;
import com.film.library.repository.CategoryRepository;
import com.film.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    @Override
    public List<Category> findAll() {
        return cateRepo.findAll();
    }

    @Override
    public Category findById(Long id) {
        return cateRepo.findById(id).get();
    }

    @Override
    public Category save(Category category) {
        return cateRepo.save(category);
    }

    @Override
    public Category update(Category category) {
        Category newCategory = cateRepo.findById(category.getId()).get();
        newCategory.setName(category.getName());
        newCategory.setDeleted(category.isDeleted());
        newCategory.setActivated(category.isActivated());
        return cateRepo.save(newCategory);
    }

    @Override
    public void deleteById(Long id) {
        Category category = cateRepo.findById(id).get();
        category.setDeleted(true);
        category.setActivated(false);
        cateRepo.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = cateRepo.findById(id).get();
        category.setDeleted(false);
        category.setActivated(true);
        cateRepo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return cateRepo.findAllByActivated();
    }

    @Override
    public List<CategoryDTO> getCategoryDTO() {
        return cateRepo.getNumberProductsInCategory();
    }


}
