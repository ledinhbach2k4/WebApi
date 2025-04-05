package com.example.techtopstore.repository;

import com.example.techtopstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findByUserId(int userId);
    @Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.status = ?2")
    Order findByUserAndStatus(int userId, String status);
}