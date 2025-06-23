package com.talection.talection.controller.userrelated;

import com.talection.talection.dto.replies.TeacherReply;
import com.talection.talection.dto.requests.SignUpRequest;
import com.talection.talection.dto.requests.UpdateUserRequest;
import com.talection.talection.enums.AuthProvider;
import com.talection.talection.enums.Role;
import com.talection.talection.exception.UserAlreadyExistsException;
import com.talection.talection.exception.UserNotFoundException;
import com.talection.talection.model.userrelated.User;
import com.talection.talection.security.AccessUserDetails;
import com.talection.talection.service.userrelated.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to add a new user.
     *
     * @param signUpRequest the request containing user details
     * @return ResponseEntity indicating success or failure
     */
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

                Long id = userService.addUser(user, signUpRequest.getPassword());
                logger.info("User added successfully with id: {}", id);
                return ResponseEntity.ok(id.toString());
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

    /**
     * Endpoint to retrieve the current user's details.
     *
     * @return ResponseEntity containing the current user's details or an error status
     */
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/get-me")
    public ResponseEntity<User> getCurrentUser() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity.ok(userService.getUserByEmail(userDetails.getUsername()));
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * Endpoint to update the current user's details.
     *
     * @param request the request containing updated user details
     * @return ResponseEntity indicating success or failure
     */
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @PutMapping("/update-me")
    public ResponseEntity<String> updateCurrentUser(@RequestBody UpdateUserRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Update request cannot be null");
        }
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            return ResponseEntity.badRequest().body("First name must not be null or empty");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body("Last name must not be null or empty");
        }
        if (request.getGender() == null) {
            return ResponseEntity.badRequest().body("Gender must not be null");
        }

        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            userService.updateUser(request, userDetails.getId());
            logger.info("User updated successfully with id: {}", userDetails.getId());
            return ResponseEntity.ok("User updated successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid update request");
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body("User not found");
        }
    }

    /**
     * Endpoint to delete the current user.
     *
     * @return ResponseEntity indicating success or failure
     */
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @DeleteMapping("/delete-me")
    public ResponseEntity<String> deleteCurrentUser() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            userService.deleteUserByEmail(userDetails.getUsername());
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid user deletion request");
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body("User not found");
        }
    }

    /**
     * Endpoint to retrieve all users. This endpoint is restricted to users with ADMIN authority.
     *
     * @return ResponseEntity containing a collection of users or an error status
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<Collection<User>> getAllUsers() {
        try {
            Collection<User> users = userService.getAllUsers();
            logger.info("Retrieved all users successfully");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error retrieving users: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/teachers")
    public ResponseEntity<Collection<TeacherReply>> getAllTeachers() {
        try {
            Collection<TeacherReply> teachers = userService.getAllTeachers();
            logger.info("Retrieved all teachers successfully");
            return ResponseEntity.ok(teachers);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving teachers: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving teachers: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint to update a user's role. This endpoint is restricted to users with ADMIN authority.
     *
     * @param id the ID of the user to update
     * @param role the new role to assign to the user
     * @return ResponseEntity indicating success or failure
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-role/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Long id, @RequestBody Role role) {
        if (id == null || role == null) {
            return ResponseEntity.badRequest().body("ID and role must not be null");
        }
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(userDetails.getId(), id)) {
            return ResponseEntity.badRequest().body("Cannot update your own role");
        }

        try {
            userService.updateUserRole(id, role);
            logger.info("User role updated successfully for user ID: {}", id);
            return ResponseEntity.ok("User role updated successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error updating user role: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid user ID or role");
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body("User not found");
        }
    }
}