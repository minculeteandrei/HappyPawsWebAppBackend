package com.happypaws.services;

import com.happypaws.domain.Appointment;
import com.happypaws.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findUserByUsername(String username);
}
