package com.example.techtopstore.service;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        return orderRepository.findById(id);
    }

    public Order addOrder(Order order){
        orderRepository.save(order);
        return order;
    }

    public Order updateOrder(int id, Order order){
        Order existingOrder = orderRepository.findById(id);
        if (existingOrder != null){
            order.setId(id);
            orderRepository.save(order);
            return order;
        }
        return null;
    }

    public void deleteOrder(int id){
        Order existingOrder = orderRepository.findById(id);
        if (existingOrder != null){
            orderRepository.deleteById(id);
        }
    }

    public Order getOrderByStatus(int userId, String status){
        return orderRepository.findByUserAndStatus(userId, status);
    }
}
