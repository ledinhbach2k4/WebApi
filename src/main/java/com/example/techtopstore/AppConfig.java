package com.example.techtopstore;

import com.example.techtopstore.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shop");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserDAO userDAO(JdbcTemplate jdbcTemplate) {
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.setJdbcTemplate(jdbcTemplate);
        return userDAO;
    }

    @Bean
    public CategoryDAO categoryDAO(JdbcTemplate jdbcTemplate) {
        CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
        categoryDAO.setJdbcTemplate(jdbcTemplate);
        return categoryDAO;
    }

    @Bean
    public ProductDAO productDAO(JdbcTemplate jdbcTemplate) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        productDAO.setJdbcTemplate(jdbcTemplate);
        return productDAO;
    }

    @Bean
    public OrderDAO orderDAO(JdbcTemplate jdbcTemplate) {
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.setJdbcTemplate(jdbcTemplate);
        return orderDAO;
    }

    @Bean
    public OrderItemDAO orderItemDAO(JdbcTemplate jdbcTemplate) {
        OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
        orderItemDAO.setJdbcTemplate(jdbcTemplate);
        return orderItemDAO;
    }
}