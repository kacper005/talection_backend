package com.talection.talection.controller;

import com.talection.talection.dto.SignUpRequest;
import com.talection.talection.enums.AuthProvider;
import com.talection.talection.enums.Role;
import com.talection.talection.exception.UserAlreadyExistsException;
import com.talection.talection.model.User;
import com.talection.talection.security.AccessUserDetails;
import com.talection.talection.service.UserService;
import jakarta.persistence.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return ResponseEntity.badRequest().body("Sign-up request cannot be null");
        }
        if (signUpRequest.getRole() == Role.ADMIN) {
            return ResponseEntity.status(401).body("Unauthorized: Admin role cannot be used for sign-up");
        }

        User user = new User();
        if (signUpRequest.getAuthProvider() == AuthProvider.LOCAL) {
            try {
                user.setAuthProvider(AuthProvider.LOCAL);

                if (signUpRequest.getRole() == Role.STUDENT || signUpRequest.getRole() == Role.TEACHER) {
                    user.setRole(signUpRequest.getRole());
                } else {
                    return ResponseEntity.badRequest().body("Invalid role for sign-up");
                }

                user.setFirstName(signUpRequest.getFirstName());
                user.setLastName(signUpRequest.getLastName());
                user.setEmail(signUpRequest.getEmail());
                user.setGender(signUpRequest.getGender());

                userService.addUser(user, signUpRequest.getPassword());
                logger.info("User added successfully with email: {}", signUpRequest.getEmail());
                return ResponseEntity.ok("User added successfully");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid sign-up request");
            } catch (UserAlreadyExistsException e) {
                logger.error("User already exists with email: {}", signUpRequest.getEmail());
                return ResponseEntity.status(409).body("User already exists with this email");
            }
        }

        logger.error("Unsupported authentication provider: {}", signUpRequest.getAuthProvider());
        return ResponseEntity.badRequest().body("Unsupported authentication provider");
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
}
