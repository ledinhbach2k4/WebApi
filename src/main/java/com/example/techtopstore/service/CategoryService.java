package com.example.techtopstore.service;

import com.example.techtopstore.model.Category;
import com.example.techtopstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCateories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id){
        return categoryRepository.findById(id);
    }

    public Category addCategory(Category product){
        categoryRepository.save(product);
        return product;
    }

    public Category updateCategory(int id, Category product){
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory != null){
            product.setId(id);
            categoryRepository.save(product);
            return product;
        }
        return null;
    }

    public void deleteCategory(int id){
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory != null){
            categoryRepository.deleteById(id);
        }
    }
}
