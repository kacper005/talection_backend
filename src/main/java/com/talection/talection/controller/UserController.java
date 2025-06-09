package com.talection.talection.controller;

import com.talection.talection.dto.SignUpRequest;
import com.talection.talection.model.User;
import com.talection.talection.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> addUser(SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return Optional.empty();
        }

        //TODO: Implement
        return null;
    }
}
