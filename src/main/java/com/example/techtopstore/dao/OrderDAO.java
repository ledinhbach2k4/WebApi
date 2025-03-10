package com.example.techtopstore.dao;

import com.example.techtopstore.model.Order;
import java.util.List;

public interface OrderDAO {
    void addOrder(Order order);
    Order getOrderById(int id);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int id);
    List<Order> findByUserId(int userId);
}