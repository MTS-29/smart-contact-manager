package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page handeller");

        // Sending data to view
        model.addAttribute("name", "Substring array");
        model.addAttribute("youtube", "Dummy channel");
        model.addAttribute("youtubelink", "https://www.youtube.com/watch?v=3p__WB2Kuls&list=PL0zysOflRCemNS641tpw66bcaFylyIGsA&index=6");
        model.addAttribute("github", "Dummy github");
        return "Home"; 
        
    }
}
