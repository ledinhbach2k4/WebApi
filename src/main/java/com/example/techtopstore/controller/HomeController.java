package com.example.techtopstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Trả về file index.html trong templates
    }

    @GetMapping("/shop")
    public String shop() {
        return "shop"; // Placeholder, bạn cần tạo shop.html nếu muốn
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // Placeholder
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Placeholder
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart"; // Placeholder
    }

    @GetMapping("/sproduct")
    public String singleProduct() {
        return "sproduct"; // Placeholder
    }
}