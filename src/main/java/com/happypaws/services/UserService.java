package com.happypaws.services;

import com.happypaws.domain.User;

public interface UserService {
    void save(User user);
    User findUserByUsername(String username);
}
