package com.example.techtopstore.controller.api;

import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class ApiOrderItemController {
    @Autowired
    private OrderItemService categoryService;

    // GET: Lấy danh sách products
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems(){
        List<OrderItem> categories = categoryService.getAllOrderItems();
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable("id") int id){
        OrderItem product = categoryService.getOrderItemById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem product){
        try{
            OrderItem newOrderItem = categoryService.addOrderItem(product);
            return new ResponseEntity<>(newOrderItem, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable("id") int id, @RequestBody OrderItem product){
        try{
            OrderItem updatedOrderItem = categoryService.updateOrderItem(id, product);
            if(updatedOrderItem == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable("id") int id){
        categoryService.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
