package com.film.library.service;

import com.film.library.dto.ProductDTO;
import com.film.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    List<Product> findAll();
    List<ProductDTO> products();
    List<ProductDTO> allProduct();
    Product findById(Long id);
    List<Product> sortHighestInCategory(Long categoryId);
    List<Product> sortHighest();
    List<Product> sortLowestInCategory(Long categoryId);
    List<Product> sortLowest();
    Product save(MultipartFile imageProduct, ProductDTO productDto);
    Product update(MultipartFile imageProduct, ProductDTO productDto);
    ProductDTO getById(Long id);
    Page<ProductDTO> pageProduct(int pageNo);
    Page<ProductDTO> searchProducts(int pageNo, String keyword);
    List<Product> getProductsByCategory(Long categoryId);
    /*Customer*/
    List<Product> findProducts(String keyword);

    void deleteById(Long id);
    void enableById(Long id);
}
