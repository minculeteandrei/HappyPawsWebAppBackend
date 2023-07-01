package com.happypaws.restcontrollers;

import com.happypaws.domain.Order;
import com.happypaws.domain.User;
import com.happypaws.services.OrderService;
import com.happypaws.services.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderRestController {
    private final UserService userService;
    private final OrderService orderService;

    public OrderRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> postOrder(@RequestParam String username, @RequestBody Order order) {
        JSONObject result = new JSONObject();
        try {
            User user = this.userService.findUserByUsername(username);
            Order newOder = new Order(order.getItems(), user);

            this.orderService.save(newOder);
            result.put("success", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            result.put("error", "User was not found in db");
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
