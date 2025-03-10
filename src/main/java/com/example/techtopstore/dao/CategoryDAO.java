package com.example.techtopstore.dao;

import com.example.techtopstore.model.Category;
import java.util.List;

public interface CategoryDAO {
    void addCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    void updateCategory(Category category);
    void deleteCategory(int id);
    Category findByName(String name);
}