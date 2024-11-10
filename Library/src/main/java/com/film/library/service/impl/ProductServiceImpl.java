package com.film.library.service.impl;

import com.film.library.dto.ProductDTO;
import com.film.library.model.Product;
import com.film.library.repository.ProductRepository;
import com.film.library.service.ProductService;
import com.film.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> products() {
        return map(productRepository.getAllProduct());
    }



    @Override
    public List<ProductDTO> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDtos = map(products);
        return productDtos;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> sortHighestInCategory(Long categoryId) {
        return productRepository.sortHighestPriceInCategory(categoryId);
    }

    @Override
    public List<Product> sortHighest() {
        return productRepository.sortHighestPrice();
    }

    @Override
    public List<Product> sortLowestInCategory(Long categoryId) {
        return productRepository.sortLowPriceInCategory(categoryId);
    }

    @Override
    public List<Product> sortLowest() {
        return productRepository.sortLowestPrice();
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDTO productDto) {
        try {
            Product product = new Product();
            if(imageProduct == null){
                product.setImage(null);
            }else{
                if(imageUpload.uploadImage(imageProduct)){
                    System.out.println("Upload successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDTO productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadImage(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDTO getById(Long id) {
        ProductDTO productDto = new ProductDTO();
        Product product = productRepository.getById(id);
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        return productDto;
    }

    @Override
    public Page<ProductDTO> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDTO> productDto = map(productRepository.getAllProduct());
        return toPage(productDto, pageable);
    }

    @Override
    public Page<ProductDTO> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDTO> productDto = map(productRepository.searchProductsList(keyword));
        return toPage(productDto, pageable);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.getProductsByCategory(categoryId);
    }

    @Override
    public List<Product> findProducts(String keyword) {
        return productRepository.findProducts(keyword);
    }

    private Page<ProductDTO> toPage(List<ProductDTO> list , Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List<ProductDTO> subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }


    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).get();
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    private List<ProductDTO> map(List<Product> products){
        List<ProductDTO> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDTO productDto = new ProductDTO();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
