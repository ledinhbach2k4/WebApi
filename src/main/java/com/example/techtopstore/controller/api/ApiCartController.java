package com.example.techtopstore.controller.api;

import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ApiCartController {
    @Autowired
    OrderItemService orderItemService;

    @PostMapping("/add-to-cart")
    ResponseEntity<OrderItem> addToCart(
            @RequestHeader("User-Id") int userId,
            @RequestBody OrderItem orderItem
            ){
        //Tim trong orders co order nao chua userId va status = pending khong
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }
}
