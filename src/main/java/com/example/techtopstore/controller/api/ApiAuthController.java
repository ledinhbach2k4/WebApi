package com.example.techtopstore.controller.api;

import com.example.techtopstore.dto.ApiResponse;
import com.example.techtopstore.dto.RegisterRequest;
import com.example.techtopstore.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ApiAuthController {
    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest){
        if(registerRequest.getEmail().equals("admin@gmail.com")){
            return new ApiResponse<>(500, "Email da ton tai", null);
        }
        return new ApiResponse<>(200, "Register success", null);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password){
        if(email.equals("admin@gmail.com") && password.equals("123")){
            return new ResponseEntity<>(new User(email, password, "ADMIN"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
