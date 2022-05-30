package com.happypaws.controllers;

import com.happypaws.domain.User;
import com.happypaws.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(Model m){
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String createUser(User user, Model m){
        if(userService.findUserByUsername(user.getUsername()) != null){
            m.addAttribute("registerError", true);
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }
}
