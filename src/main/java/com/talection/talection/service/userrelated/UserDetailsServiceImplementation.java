package com.talection.talection.service.userrelated;

import com.talection.talection.model.userrelated.User;
import com.talection.talection.repository.userrelated.UserRepository;
import com.talection.talection.security.AccessUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImplementation.class);

    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their email address.
     *
     * @param email the email address of the user
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            logger.error("User not found: {}", email);
            throw new UsernameNotFoundException("User not found");
        }

        logger.info("User found: {}", email);
        return new AccessUserDetails(user.get());
    }
}
