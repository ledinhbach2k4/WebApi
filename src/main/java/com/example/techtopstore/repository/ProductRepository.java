package com.example.techtopstore.repository;

import com.example.techtopstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int productId);
    List<Product> findByCategoryId(int categoryId);
}