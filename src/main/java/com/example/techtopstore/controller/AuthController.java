package com.example.techtopstore.controller;


import com.example.techtopstore.model.User;
import com.example.techtopstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

//@Controller
//public class AuthController {
//
//    // Danh sách tạm lưu người dùng (thay cho database)
//    private List<User> users = new ArrayList<>();
//
//    public AuthController() {
//        // Thêm người dùng mặc định
//        User defaultUser = new User();
//        defaultUser.setUsername("admin");
//        defaultUser.setPassword("123456");
//        users.add(defaultUser);
//    }
//
//    // Hiển thị form đăng nhập
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//    // Xử lý đăng nhập
//    @PostMapping("/login")
//    public String processLogin(User user, Model model) {
//        boolean loginSuccess = false;
//        for (User registeredUser : users) {
//            if (registeredUser.getUsername().equals(user.getUsername()) &&
//                    registeredUser.getPassword().equals(user.getPassword())) {
//                loginSuccess = true;
//                break;
//            }
//        }
//
//        if (loginSuccess) {
//            model.addAttribute("message", "Đăng nhập thành công!");
//            model.addAttribute("messageColor", "green");
//        } else {
//            model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
//            model.addAttribute("messageColor", "red");
//        }
//        return "login";
//    }
//
//    // Hiển thị form đăng ký
//    @GetMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    // Xử lý đăng ký
//    @PostMapping("/register")
//    public String processRegister(User user, Model model) {
//        // Kiểm tra xem username đã tồn tại chưa
//        for (User registeredUser : users) {
//            if (registeredUser.getUsername().equals(user.getUsername())) {
//                model.addAttribute("message", "Tên đăng nhập đã tồn tại!");
//                model.addAttribute("messageColor", "red");
//                return "register";
//            }
//        }
//
//        // Thêm người dùng mới vào danh sách
//        users.add(user);
//        model.addAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
//        model.addAttribute("messageColor", "green");
//        return "register";
//    }
//}


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String processLogin(User user, Model model) {
        User registeredUser = userService.getUserByEmail(user.getEmail());
        if (registeredUser != null && user.getPassword().equals(registeredUser.getPassword())) {
            return "redirect:/dashboard"; // Chuyển hướng đến dashboard
        } else {
            model.addAttribute("message", "Email hoặc mật khẩu không đúng!");
            model.addAttribute("messageColor", "red");
            return "login";
        }
    }

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String processRegister(User user, Model model) {
        // Kiểm tra xem email đã tồn tại chưa
        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Email đã tồn tại!");
            model.addAttribute("messageColor", "red");
            return "register";
        }

        // Đặt role mặc định là "user" nếu không được chỉ định
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }
        userService.addUser(user);
        model.addAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
        model.addAttribute("messageColor", "green");
        return "register";
    }
}