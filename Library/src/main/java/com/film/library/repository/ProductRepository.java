package com.film.library.repository;

import com.film.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> getAllProduct();

    @Query("select p from Product p")
    Page<Product> pageProducts(Pageable pageable);

    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    Page<Product> searchProducts(String keyword, Pageable pageable);

    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    List<Product> searchProductsList(String keyword);

    /*customer*/
    @Query("select p from Product p where lower(p.name) like lower(concat('%', ?1, '%')) and p.is_activated=true and p.is_deleted=false")
    List<Product> findProducts(String keyword);

    @Query("select p from Product p where p.category.id=?1 and p.is_activated=true and p.is_deleted=false")
    List<Product> getProductsByCategory(Long categoryId);

    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false and p.category.id = ?1 order by p.costPrice desc")
    List<Product> sortHighestPriceInCategory(Long categoryId);

    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false order by p.costPrice desc")
    List<Product> sortHighestPrice();

    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false and p.category.id = ?1 order by p.costPrice asc")
    List<Product> sortLowPriceInCategory(Long categoryId);

    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false order by p.costPrice asc")
    List<Product> sortLowestPrice();
}
