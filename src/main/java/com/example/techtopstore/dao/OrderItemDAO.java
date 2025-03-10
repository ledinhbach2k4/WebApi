package com.example.techtopstore.dao;

import com.example.techtopstore.model.OrderItem;
import java.util.List;

public interface OrderItemDAO {
    void addOrderItem(OrderItem orderItem);
    OrderItem getOrderItemById(int id);
    List<OrderItem> getAllOrderItems();
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(int id);
    List<OrderItem> findByOrderId(int orderId);
}