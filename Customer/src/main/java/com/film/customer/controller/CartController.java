package com.film.customer.controller;

import com.film.library.model.Customer;
import com.film.library.model.Product;
import com.film.library.model.ShoppingCart;
import com.film.library.service.CustomerService;
import com.film.library.service.ProductService;
import com.film.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("title", "Cart");
        return "cart";
    }


    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Long productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpSession session,
            HttpServletRequest request){

        if(principal == null){
            return "redirect:/login";
        }
        Product product = productService.findById(productId);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = cartService.addItemToCart(product, quantity, customer);
        session.setAttribute("shoppingCart", cart);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping(value = "/update-cart", params="action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal,
                             HttpSession session
    ){

        if(principal == null){
            session.removeAttribute("shoppingCart");
            session.removeAttribute("customer");
            return "redirect:/login";
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Product product = productService.findById(productId);
            ShoppingCart cart = cartService.updateItemInCart(product, quantity, customer);
            session.setAttribute("shoppingCart", cart);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }


    @PostMapping(value = "/update-cart", params="action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long productId,
                                     Model model,
                                     Principal principal,
                                     HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Product product = productService.findById(productId);
            ShoppingCart cart = cartService.deleteItemFromCart(product, customer);
            model.addAttribute("shoppingCart", cart);
            session.setAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }

}
