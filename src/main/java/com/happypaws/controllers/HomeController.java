package com.happypaws.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(){return "home";}

    @GetMapping("login")
    public String getLoginPage(){return "login";}

    @GetMapping("login-error")
    public String getLoginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
