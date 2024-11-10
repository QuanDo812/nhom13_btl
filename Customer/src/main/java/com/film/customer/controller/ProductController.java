package com.film.customer.controller;

import com.film.library.model.Product;
import com.film.library.service.CategoryService;
import com.film.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value="/shop")
    public String shop(Model model, HttpSession session, Principal principal) {

        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.products());
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        return "category";
    }

    @GetMapping(value="/products-in-category/{id}")
    public String productsInCategory(@PathVariable("id") Long categoryId, Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.getProductsByCategory(categoryId));
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        return "category";
    }

    @GetMapping(value="/product-detail/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Product Detail");
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("products", productService.findAll());
        return "product-details";
    }

    @GetMapping(value="/find-products")
    public String findProducts(Model model, @RequestParam("keyword") String keyword) {
        model.addAttribute("title", "Find Products");
        model.addAttribute("products", productService.findProducts(keyword));
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        return "find-products";
    }

    @GetMapping(value="/filter-highest")
    public String filterHighest(Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.sortHighest());
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        model.addAttribute("selectedOption", "highest");
        return "category";
    }

    @GetMapping(value="/filter-highest/{categoryId}")
    public String filterHighestCate(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.sortHighestInCategory(categoryId));
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        model.addAttribute("selectedOption", "highest");
        return "category";
    }

    @GetMapping(value="/filter-lowest")
    public String filterLowest(Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.sortLowest());
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        model.addAttribute("selectedOption", "lowest");
        return "category";
    }

    @GetMapping(value="/filter-lowest/{categoryId}")
    public String filterLowestCate(@PathVariable Long categoryId, Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productService.sortLowestInCategory(categoryId));
        model.addAttribute("categoryDtos", categoryService.getCategoryDTO());
        model.addAttribute("selectedOption", "lowest");
        return "category";
    }

}
