package com.film.admin.controller;


import com.film.library.dto.AdminDTO;
import com.film.library.model.Admin;
import com.film.library.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping(value = "/login")
    public String loginForm(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping(value = "/index")
    public String index(Model model, HttpSession session, Principal principal){
        model.addAttribute("title", "Homepage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        session.setAttribute("username", principal.getName());
        return "index";
    }

    @GetMapping(value = "/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDTO());
        return "register";
    }

    @PostMapping(value = "/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDTO adminDTO,

                              BindingResult result,
                              Model model
                              ){
        try {

            if(result.hasErrors()){
                model.addAttribute("adminDto", adminDTO);
                result.toString();
                
                return "register";
            }
            String username = adminDTO.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto", adminDTO);
                System.out.println("admin not null");

                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if(adminDTO.getPassword().equals(adminDTO.getRepeatPassword())){
                adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
                adminService.save(adminDTO);
                System.out.println("success");

                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDTO);
            }else{
                model.addAttribute("adminDto", adminDTO);

                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");

                System.out.println("password not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }

}

