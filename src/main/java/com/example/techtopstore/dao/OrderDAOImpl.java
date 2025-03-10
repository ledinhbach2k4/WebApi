package com.example.techtopstore.dao;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (code, status, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getCode(), order.getStatus(), order.getUser().getId());
    }

    @Override
    public Order getOrderById(int id) {
        String sql = "SELECT o.*, u.id as user_id, u.email, u.password, u.role " +
                "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderRowMapper());
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT o.*, u.id as user_id, u.email, u.password, u.role " +
                "FROM orders o JOIN users u ON o.user_id = u.id";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET code = ?, status = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getCode(), order.getStatus(), order.getUser().getId(), order.getId());
    }

    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Order> findByUserId(int userId) {
        String sql = "SELECT o.*, u.id as user_id, u.email, u.password, u.role " +
                "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new OrderRowMapper());
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setCode(rs.getString("code"));
            order.setStatus(rs.getString("status"));

            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            order.setUser(user);

            return order;
        }
    }
}