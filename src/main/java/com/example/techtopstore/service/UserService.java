package com.example.techtopstore.service;

import com.example.techtopstore.dao.UserDAO;
import com.example.techtopstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User addUser(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        userDAO.addUser(user);
        return user;
    }

    public User updateUser(int id, User user) {
        User existingUser = userDAO.getUserById(id);
        if (existingUser != null) {
            user.setId(id);
            User userWithEmail = userDAO.findByEmail(user.getEmail());
            if (userWithEmail != null && userWithEmail.getId() != id) {
                throw new RuntimeException("Email đã tồn tại!");
            }
            userDAO.updateUser(user);
            return user;
        }
        return null;
    }

    public User patchUser(int id, User userUpdates) {
        User existingUser = userDAO.getUserById(id);
        if (existingUser != null) {
            if (userUpdates.getEmail() != null) {
                User userWithEmail = userDAO.findByEmail(userUpdates.getEmail());
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
            userDAO.updateUser(existingUser);
            return existingUser;
        }
        return null;
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}