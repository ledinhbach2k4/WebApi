package com.example.techtopstore.repository;

import com.example.techtopstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Đảm bảo import Optional

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id); // Sửa lại thành Optional<User>
    User findByEmail(String email);
}
