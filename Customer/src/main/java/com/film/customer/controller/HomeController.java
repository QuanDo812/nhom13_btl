package com.film.customer.controller;

import com.film.library.model.Customer;
import com.film.library.model.ShoppingCart;
import com.film.library.service.CategoryService;
import com.film.library.service.CustomerService;
import com.film.library.service.ProductService;
import com.film.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value={"/index", "/"})
    public String home(Model model, Principal principal, HttpSession session) {
        if(principal != null){
            session.setAttribute("username", principal.getName());
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            ShoppingCart shoppingCart = customer.getShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);
        }else{
            session.removeAttribute("shoppingCart");
            session.removeAttribute("username");
        }
        model.addAttribute("title", "Home");
        return "index-1";
    }

}
