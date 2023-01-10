package com.happypaws.controllers;

import com.happypaws.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getAdminPage(Model m){
        m.addAttribute("product", new Product());
        return "admin";
    }
}
