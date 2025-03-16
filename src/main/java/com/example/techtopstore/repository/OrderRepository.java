package com.example.techtopstore.repository;

import com.example.techtopstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findByUserId(int userId);
}