package com.example.techtopstore.service;

import com.example.techtopstore.model.User;
import com.example.techtopstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    // Các phương thức hiện có của UserService
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.getReferenceById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // Mã hóa mật khẩu trước khi lưu vào database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Đặt mật khẩu đã mã hóa vào user

        userRepository.save(user);  // Lưu vào DB
        return user;
    }

    public User updateUser(int id, User user) {
        User existingUser = userRepository.getReferenceById(id);
        if (existingUser != null) {
            user.setId(id);
            User userWithEmail = userRepository.findByEmail(user.getEmail());
            if (userWithEmail != null && userWithEmail.getId() != id) {
                throw new RuntimeException("Email đã tồn tại!");
            }
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User patchUser(int id, User userUpdates) {
        User existingUser = userRepository.getReferenceById(id);
        if (existingUser != null) {
            if (userUpdates.getEmail() != null) {
                User userWithEmail = userRepository.findByEmail(userUpdates.getEmail());
                if (userWithEmail != null && userWithEmail.getId() != id) {
                    throw new RuntimeException("Email đã tồn tại!");
                }
                existingUser.setEmail(userUpdates.getEmail());
            }
            if (userUpdates.getPassword() != null) {
                existingUser.setPassword(userUpdates.getPassword());
            }
            if (userUpdates.getRole() != null) {
                existingUser.setRole(userUpdates.getRole());
            }
            userRepository.save(existingUser);
            return existingUser;
        }
        return null;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}