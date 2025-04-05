package com.example.techtopstore.controller;

import com.example.techtopstore.model.User;
import com.example.techtopstore.service.CartService;
import com.example.techtopstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("cartCount")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Thêm PasswordEncoder ở đây

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartCount")
    public int getCartCount() {
        return cartService.getTotalCartItems();  // Lấy số lượng sản phẩm trong giỏ hàng
    }

    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String processLogin(User user, Model model) {
        logger.info("Attempting to log in user: {}", user.getEmail());
        User registeredUser = userService.getUserByEmail(user.getEmail());
        if (registeredUser != null) {
            logger.info("User found: {}", registeredUser.getEmail());
            if (passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())) {
                logger.info("Password matches for user: {}", registeredUser.getEmail());
                return "redirect:/dashboard"; // Redirect to dashboard
            } else {
                logger.warn("Password does not match for user: {}", registeredUser.getEmail());
            }
        } else {
            logger.warn("User not found: {}", user.getEmail());
        }
        model.addAttribute("message", "Email or password is incorrect!");
        model.addAttribute("messageColor", "red");
        return "login";
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
        logger.info("Registering user: {}", user.getEmail());
        // Kiểm tra xem email đã tồn tại chưa
        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Email đã tồn tại!");
            model.addAttribute("messageColor", "red");
            return "register";
        }

        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Đặt role mặc định là "user" nếu không được chỉ định
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }

        userService.addUser(user);
        model.addAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
        model.addAttribute("messageColor", "green");
        logger.info("User registered successfully: {}", user.getEmail());
        return "redirect:/login";
    }
}
