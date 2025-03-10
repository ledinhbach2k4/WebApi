package com.example.techtopstore.dao;

import com.example.techtopstore.model.Product;
import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);
    List<Product> findByCategoryId(int categoryId);
}