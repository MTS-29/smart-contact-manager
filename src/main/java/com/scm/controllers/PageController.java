package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entites.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code With Durgesh");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }

    // about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    // contact page

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Processing register
    @PostMapping("/do-register")
    public String processRegister(@ModelAttribute UserForm userForm){
        // System.out.println(userForm);

        User savedUser = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .phoneNumber(userForm.getPhoneNumber())
        .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fadinaiqbl%2Fdefault-icon-pfp%2F&psig=AOvVaw3Na6ci5iH0rRN3EnNQZbI3&ust=1717354214327000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOip-JWJu4YDFQAAAAAdAAAAABAE")
        .build();

        userService.saveUser(savedUser); 
        System.out.println(savedUser);
        return "redirect:/register";
    }
}