package com.happypaws.restcontrollers;

import com.happypaws.domain.User;
import com.happypaws.services.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable("username") String username) {
        JSONObject result = new JSONObject();
        try{
            User user = this.userService.findUserByUsername(username);
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            result.put("error", "User was not found in db");
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
