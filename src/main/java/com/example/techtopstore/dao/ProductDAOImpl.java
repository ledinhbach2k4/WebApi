package com.example.techtopstore.dao;

import com.example.techtopstore.model.Category;
import com.example.techtopstore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, thumbnail, price, quantity, category_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getThumbnail(),
                product.getPrice(), product.getQuantity(), product.getCategory().getId(), product.getCreatedAt());
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT p.*, c.id as category_id, c.name as category_name, c.thumbnail as category_thumbnail " +
                "FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT p.*, c.id as category_id, c.name as category_name, c.thumbnail as category_thumbnail " +
                "FROM products p JOIN categories c ON p.category_id = c.id";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, thumbnail = ?, price = ?, quantity = ?, category_id = ?, created_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getThumbnail(),
                product.getPrice(), product.getQuantity(), product.getCategory().getId(), product.getCreatedAt(), product.getId());
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        String sql = "SELECT p.*, c.id as category_id, c.name as category_name, c.thumbnail as category_thumbnail " +
                "FROM products p JOIN categories c ON p.category_id = c.id WHERE p.category_id = ?";
        return jdbcTemplate.query(sql, new Object[]{categoryId}, new ProductRowMapper());
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setThumbnail(rs.getString("thumbnail"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("category_name"));
            category.setThumbnail(rs.getString("category_thumbnail"));
            product.setCategory(category);

            return product;
        }
    }
}