package com.example.techtopstore.dao;

import com.example.techtopstore.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (name, thumbnail) VALUES (?, ?)";
        jdbcTemplate.update(sql, category.getName(), category.getThumbnail());
    }

    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CategoryRowMapper());
    }

    @Override
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    @Override
    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, thumbnail = ? WHERE id = ?";
        jdbcTemplate.update(sql, category.getName(), category.getThumbnail(), category.getId());
    }

    @Override
    public void deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Category findByName(String name) {
        String sql = "SELECT * FROM categories WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new CategoryRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setThumbnail(rs.getString("thumbnail"));
            return category;
        }
    }
}