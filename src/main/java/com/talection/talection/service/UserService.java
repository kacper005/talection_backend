package com.talection.talection.service;

import com.talection.talection.enums.AuthProvider;
import com.talection.talection.enums.Role;
import com.talection.talection.exception.UserAlreadyExistsException;
import com.talection.talection.exception.UserNotFoundException;
import com.talection.talection.model.User;
import com.talection.talection.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user to the system.
     *
     * @param user     the user to add
     * @param password the password for the user
     * @throws UserAlreadyExistsException if a user with the same email or google ID already exists
     */
    public void addUser(User user, String password) throws UserAlreadyExistsException {
        if (password == null || password.isEmpty() || user == null) {
            logger.error("Invalid user or password provided");
            throw new IllegalArgumentException("User and password must not be null or empty");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.error("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException("User with the same email or Google ID already exists");
        }

        if (user.getCredentialId() != null && userRepository.findByCredentialId(user.getCredentialId()).isPresent()) {
            logger.error("User with Google ID {} already exists", user.getCredentialId());
            throw new UserAlreadyExistsException("User with the same email or Google ID already exists");
        }

        if (user.getAuthProvider() == AuthProvider.LOCAL) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
            user.setPassword(null); // No password for OAuth users
        }

        userRepository.save(user);
    }

    /**
     * Retrieves the role of a user based on their Credential ID.
     *
     * @param credentialId the Credential ID of the user
     * @return the role of the user
     * @throws IllegalArgumentException if the Google ID is null or empty
     * @throws UserNotFoundException if the user is not found with the given Credential ID
     */
    public Role getRoleByCredentialId(String credentialId) {
        if (credentialId == null || credentialId.isEmpty()) {
            logger.error("Google ID must not be null or empty");
            throw new IllegalArgumentException("Google ID must not be null or empty");
        }
        User user = userRepository.findByCredentialId(credentialId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with Google ID: " + credentialId));

        return user.getRole();
    }

    /**
     * Retrieves the role of a user based on their email.
     *
     * @param email the email of the user
     * @return the role of the user
     * @throws IllegalArgumentException if the email is null or empty
     * @throws UserNotFoundException if the user is not found with the given email
     */
    public Role getRoleByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email must not be null or empty");
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return user.getRole();
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the specified email
     * @throws IllegalArgumentException if the email is null or empty, or if the user is not found
     */
    public User getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email must not be null or empty");
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to delete
     * @throws IllegalArgumentException if the email is null or empty
     * @throws UserNotFoundException if the user is not found with the given email
     */
    public void deleteUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email must not be null or empty");
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }
}
