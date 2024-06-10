package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entites.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

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
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){

        System.out.println("Processing Registration");

        if(rBindingResult.hasErrors()){
            return "register";
        }

        User savedUser = new User();
        savedUser.setName(userForm.getName());
        savedUser.setEmail(userForm.getEmail());
        savedUser.setPassword(userForm.getPassword());
        savedUser.setAbout(userForm.getAbout());
        savedUser.setPhoneNumber(userForm.getPhoneNumber());
        savedUser.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fadinaiqbl%2Fdefault-icon-pfp%2F&psig=AOvVaw3Na6ci5iH0rRN3EnNQZbI3&ust=1717354214327000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOip-JWJu4YDFQAAAAAdAAAAABAE");

        userService.saveUser(savedUser); 

        Message message = Message.builder()
        .content("Registration Successful")
        .type(MessageType.green)
        .build();

        session.setAttribute("message", message);
        // System.out.println(savedUser);
        return "redirect:/register";
    }
}