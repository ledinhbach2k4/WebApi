package com.example.techtopstore.controller.api;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.model.Product;
import com.example.techtopstore.service.OrderItemService;
import com.example.techtopstore.service.OrderService;
import com.example.techtopstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ApiCartController {
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @PostMapping("/add-to-cart")
    ResponseEntity<OrderItem> addToCart(
            @RequestParam("product_id") int productId,
            @RequestParam("order_id") int orderId,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") double price
            ){
        //Tim trong orders co order nao chua userId va status = pending khong
        Order order = orderService.getOrderById(orderId);
        Product product = productService.getProductById(productId);
        OrderItem orderItem = new OrderItem(order, product, quantity, price);
        orderItemService.createOrderItem(orderItem);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }
}
