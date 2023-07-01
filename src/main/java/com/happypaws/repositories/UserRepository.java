package com.happypaws.repositories;

import com.happypaws.domain.Order;
import com.happypaws.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
