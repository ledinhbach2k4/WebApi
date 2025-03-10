package com.example.techtopstore.dao;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrder().getId(), orderItem.getProduct().getId(),
                orderItem.getQuantity(), orderItem.getPrice(), orderItem.getCreatedAt());
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        String sql = "SELECT oi.*, o.id as order_id, o.code, o.status, o.user_id, " +
                "p.id as product_id, p.name as product_name, p.description as product_description, p.thumbnail, p.price as product_price, p.quantity as product_quantity, p.category_id, p.created_at as product_created_at " +
                "FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "JOIN products p ON oi.product_id = p.id WHERE oi.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderItemRowMapper());
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        String sql = "SELECT oi.*, o.id as order_id, o.code, o.status, o.user_id, " +
                "p.id as product_id, p.name as product_name, p.description as product_description, p.thumbnail, p.price as product_price, p.quantity as product_quantity, p.category_id, p.created_at as product_created_at " +
                "FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "JOIN products p ON oi.product_id = p.id";
        return jdbcTemplate.query(sql, new OrderItemRowMapper());
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET order_id = ?, product_id = ?, quantity = ?, price = ?, created_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, orderItem.getOrder().getId(), orderItem.getProduct().getId(),
                orderItem.getQuantity(), orderItem.getPrice(), orderItem.getCreatedAt(), orderItem.getId());
    }

    @Override
    public void deleteOrderItem(int id) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<OrderItem> findByOrderId(int orderId) {
        String sql = "SELECT oi.*, o.id as order_id, o.code, o.status, o.user_id, " +
                "p.id as product_id, p.name as product_name, p.description as product_description, p.thumbnail, p.price as product_price, p.quantity as product_quantity, p.category_id, p.created_at as product_created_at " +
                "FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, new OrderItemRowMapper());
    }

    private static class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(rs.getInt("id"));
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setPrice(rs.getDouble("price"));
            orderItem.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

            Order order = new Order();
            order.setId(rs.getInt("order_id"));
            order.setCode(rs.getString("code"));
            order.setStatus(rs.getString("status"));
            orderItem.setOrder(order);

            Product product = new Product();
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("product_name"));
            product.setDescription(rs.getString("product_description"));
            product.setThumbnail(rs.getString("thumbnail"));
            product.setPrice(rs.getDouble("product_price"));
            product.setQuantity(rs.getInt("product_quantity"));
            product.setCreatedAt(rs.getTimestamp("product_created_at").toLocalDateTime());
            orderItem.setProduct(product);

            return orderItem;
        }
    }
}