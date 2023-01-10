package com.happypaws.bootstrap;

import com.happypaws.domain.Role;
import com.happypaws.domain.User;
import com.happypaws.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        User user1 = new User("user1", bcrypt.encode("user1"));
        user1.getRoles().add(Role.ROLE_ADMIN);
        userService.save(user1);
    }
}
