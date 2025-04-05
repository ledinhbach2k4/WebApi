package com.example.techtopstore.service;

import com.example.techtopstore.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.example.techtopstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }


    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(int id, Product product) {
        Product existingProduct = productRepository.getReferenceById(id);
        if (existingProduct != null) {
            product.setId(id);
            productRepository.save(product);
            return product;
        }
        return null;
    }

    public void deleteProduct(int id) {
        Product existingProduct = productRepository.getReferenceById(id);
        if (existingProduct != null) {
            productRepository.deleteById(id);
        }
    }

    public List<Product> getTopProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findTopProducts(pageable);
    }

    // Lọc theo khoảng giá
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    // Sắp xếp theo các tiêu chí
    public List<Product> getSortedProducts(String sortType, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        switch (sortType) {
            case "priceAsc":
                return productRepository.sortByPriceAsc(pageable);
            case "priceDesc":
                return productRepository.sortByPriceDesc(pageable);
            case "nameAsc":
                return productRepository.sortByNameAsc(pageable);
            case "nameDesc":
                return productRepository.sortByNameDesc(pageable);
            case "rating":
                return productRepository.sortByRating(pageable);
            default:
                return getAllProducts();
        }
    }
}
