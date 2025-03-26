package com.example.techtopstore.controller.api;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.User;
import com.example.techtopstore.service.OrderService;
import com.example.techtopstore.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ApiOrderController {
    @Autowired
    private OrderService categoryService;

    // GET: Lấy danh sách products
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> categories = categoryService.getAllOrders();
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id){
        Order product = categoryService.getOrderById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order product){
        try{
            Order newOrder = categoryService.addOrder(product);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") int id, @RequestBody Order product){
        try{
            Order updatedOrder = categoryService.updateOrder(id, product);
            if(updatedOrder == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id){
        categoryService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-by-status")
    public ResponseEntity<Order> getOrderById(@RequestParam("userId") int userId, @RequestParam("status") String status){
        Order order = categoryService.getOrderByStatus(userId, status);
        if(order == null){
            //Tao moi order
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            User user = new User();
            user.setId(1);

            order = new Order();
            order.setStatus(status);
            order.setCode(Helper.generateRandomString(8));
            order.setUser(user);
            order = categoryService.addOrder(order);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
