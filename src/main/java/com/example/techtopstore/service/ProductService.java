package com.example.techtopstore.service;

import com.example.techtopstore.dao.ProductDAO;
import com.example.techtopstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public List<Product> getAllProducts(){
        return productDAO.getAllProducts();
    }
    public List<Product> getProductsByCategory(int categoryId){
        return productDAO.findByCategoryId(categoryId);
    }

    public Product getProductById(int id){
        return productDAO.getProductById(id);
    }

    public Product addProduct(Product product){
        productDAO.addProduct(product);
        return product;
    }

    public Product updateProduct(int id, Product product){
        Product existingProduct = productDAO.getProductById(id);
        if (existingProduct != null){
            product.setId(id);
            productDAO.updateProduct(product);
            return product;
        }
        return null;
    }

    public void deleteProduct(int id){
        Product existingProduct = productDAO.getProductById(id);
        if (existingProduct != null){
            productDAO.deleteProduct(id);
        }
    }
}
