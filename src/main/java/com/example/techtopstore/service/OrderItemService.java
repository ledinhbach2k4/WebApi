package com.example.techtopstore.service;

import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(int id){
        return orderItemRepository.findById(id);
    }

    public OrderItem addOrderItem(OrderItem order){
        orderItemRepository.save(order);
        return order;
    }

    public OrderItem updateOrderItem(int id, OrderItem orderItem){
        OrderItem existingOrderItem = orderItemRepository.findById(id);
        if (existingOrderItem != null){
            orderItem.setId(id);
            orderItemRepository.save(orderItem);
            return orderItem;
        }
        return null;
    }

    public void deleteOrderItem(int id){
        OrderItem existingOrderItem = orderItemRepository.findById(id);
        if (existingOrderItem != null){
            orderItemRepository.deleteById(id);
        }
    }

    public OrderItem createOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    // Thêm phương thức mới để lấy danh sách OrderItem theo orderId
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
