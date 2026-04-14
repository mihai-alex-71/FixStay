package com.fixStay.backend.controller;


import com.fixStay.backend.dto.LoginRequest;
import com.fixStay.backend.dto.RegisterRequest;
import com.fixStay.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") //http://localhost:8080/api/auth/register
    public String registerUser(@RequestBody RegisterRequest request){ // convert json to java
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest request){
        return userService.logInUser(request);
    }
}
