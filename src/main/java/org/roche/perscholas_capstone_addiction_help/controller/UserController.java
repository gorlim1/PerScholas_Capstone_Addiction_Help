package org.roche.perscholas_capstone_addiction_help.controller;


import org.roche.perscholas_capstone_addiction_help.model.User;
import org.roche.perscholas_capstone_addiction_help.service.TreatmentService;
import org.roche.perscholas_capstone_addiction_help.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
//@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home"; // Returns home.html
    }
}