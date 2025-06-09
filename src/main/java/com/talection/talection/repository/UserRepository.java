package com.talection.talection.repository;

import com.talection.talection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find
     * @return an Optional containing the found user, or empty if no user with the specified email exists
     */
    Optional<User> findByEmail(String email);
}
