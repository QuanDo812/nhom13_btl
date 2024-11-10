package com.film.library.repository;

import com.film.library.dto.CategoryDTO;
import com.film.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.activated=true and c.deleted=false")
    List<Category> findAllByActivated();

    @Query("select new com.film.library.dto.CategoryDTO(c.id, c.name, count(p.name)) from Category c left join Product p on p.category.id = c.id"
    + " where c.deleted=false and c.activated=true group by c.id")
    List<CategoryDTO> getNumberProductsInCategory();
}
