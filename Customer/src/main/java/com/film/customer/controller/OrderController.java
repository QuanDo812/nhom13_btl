package com.film.customer.controller;

import com.film.library.model.Customer;
import com.film.library.model.ShoppingCart;
import com.film.library.service.CustomerService;
import com.film.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){
            model.addAttribute("title", "My account");
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information after checkout!");
            return "account";
        }else{
            model.addAttribute("title", "Checkout");
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
        }
        return "checkout";
    }


    @GetMapping("/order")
    public String order(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Order");
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCart cart = shoppingCartService.deleteShoppingCart(shoppingCart);
        session.setAttribute("shoppingCart", cart);
        return "order";
    }

}
