package com.talection.talection.repository.userrelated;

import com.talection.talection.enums.Role;
import com.talection.talection.model.userrelated.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find
     * @return an Optional containing the found user, or empty if no user with the specified email exists
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their Google ID.
     *
     * @param credentialId the Google ID of the user to find
     * @return an Optional containing the found user, or empty if no user with the specified Google ID exists
     */
    Optional<User> findByCredentialId(String credentialId);

    /**
     * Finds all users by their role.
     *
     * @param role of the users
     * @return a collection of users with the specified role
     */
    Collection<User> findAllByRole(Role role);
}
