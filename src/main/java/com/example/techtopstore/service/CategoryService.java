package com.example.techtopstore.service;

import com.example.techtopstore.dao.CategoryDAO;
import com.example.techtopstore.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    public List<Category> getAllCateories(){
        return categoryDAO.getAllCategories();
    }

    public Category getCategoryById(int id){
        return categoryDAO.getCategoryById(id);
    }

    public Category addCategory(Category product){
        categoryDAO.addCategory(product);
        return product;
    }

    public Category updateCategory(int id, Category product){
        Category existingCategory = categoryDAO.getCategoryById(id);
        if (existingCategory != null){
            product.setId(id);
            categoryDAO.updateCategory(product);
            return product;
        }
        return null;
    }

    public void deleteCategory(int id){
        Category existingCategory = categoryDAO.getCategoryById(id);
        if (existingCategory != null){
            categoryDAO.deleteCategory(id);
        }
    }
}
