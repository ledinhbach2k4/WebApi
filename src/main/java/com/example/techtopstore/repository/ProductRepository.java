package com.example.techtopstore.repository;

import com.example.techtopstore.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int productId);
    List<Product> findByCategoryId(int categoryId);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p")
    List<Product> findTopProducts(Pageable pageable);

    // Lọc theo khoảng giá
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    // Sắp xếp theo các tiêu chí
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortByPriceAsc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortByPriceDesc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    List<Product> sortByNameAsc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.name DESC")
    List<Product> sortByNameDesc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.rating DESC")
    List<Product> sortByRating(Pageable pageable);
}
