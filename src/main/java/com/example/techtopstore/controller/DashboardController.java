package com.example.techtopstore.controller;

import com.example.techtopstore.model.User;
import com.example.techtopstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    // Hiển thị trang dashboard với danh sách users
    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User()); // Dùng để bind form thêm user
        return "admin/dashboard";
    }

    // Thêm user mới
    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user, Model model) {
        try {
            userService.addUser(user);
            model.addAttribute("message", "Thêm user thành công!");
            model.addAttribute("messageColor", "green");
        } catch (Exception e) {
            model.addAttribute("message", "Thêm user thất bại: " + e.getMessage());
            model.addAttribute("messageColor", "red");
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "dashboard";
    }

    // Hiển thị form chỉnh sửa user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("message", "User không tồn tại!");
            model.addAttribute("messageColor", "red");
        } else {
            model.addAttribute("userToEdit", user);
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "dashboard";
    }

    // Cập nhật user
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute("userToEdit") User user, Model model) {
        try {
            userService.updateUser(id, user);
            model.addAttribute("message", "Cập nhật user thành công!");
            model.addAttribute("messageColor", "green");
        } catch (Exception e) {
            model.addAttribute("message", "Cập nhật user thất bại: " + e.getMessage());
            model.addAttribute("messageColor", "red");
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "dashboard";
    }

    // Xóa user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                model.addAttribute("message", "User không tồn tại!");
                model.addAttribute("messageColor", "red");
            } else {
                userService.deleteUser(id);
                model.addAttribute("message", "Xóa user thành công!");
                model.addAttribute("messageColor", "green");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Xóa user thất bại: " + e.getMessage());
            model.addAttribute("messageColor", "red");
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "dashboard";
    }
}