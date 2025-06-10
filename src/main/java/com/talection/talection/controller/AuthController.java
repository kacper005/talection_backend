package com.talection.talection.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.talection.talection.config.GoogleTokenVerifier;
import com.talection.talection.config.JwtUtil;
import com.talection.talection.dto.AuthenticationRequest;
import com.talection.talection.enums.AuthProvider;
import com.talection.talection.service.UserDetailsServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/authenticate")
public class AuthController {
    private final UserDetailsServiceImplementation userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserDetailsServiceImplementation userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Authentication request cannot be null");
        }

        if (request.getAuthProvider() == AuthProvider.LOCAL) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
                if (userDetails == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
                String jwt = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            } catch (BadCredentialsException e) {
                logger.error("Authentication failed for user: {}", request.getEmail());
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            } catch (UsernameNotFoundException e) {
                logger.error("User not found: {}", request.getEmail());
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                logger.error("An error occurred during authentication: {}", e.getMessage());
                return new ResponseEntity<>("An error occurred during authentication", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (request.getAuthProvider() == AuthProvider.GOOGLE) {
            try {
                GoogleTokenVerifier googleTokenVerifier = new GoogleTokenVerifier();
                GoogleIdToken.Payload payload = googleTokenVerifier.verifyToken(request.getCredentialId());
                if (payload == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google ID token");
                }

                UserDetails userDetails = userDetailsService.loadUserByUsername(payload.getEmail());
                if (userDetails == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
                String jwt = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            } catch (GeneralSecurityException e) {
                logger.error("Security error during Google authentication: {}", e.getMessage());
                return new ResponseEntity<>("Security error during Google authentication: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                logger.error("IO error during Google authentication: {}", e.getMessage());
                return new ResponseEntity<>("IO error during Google authentication: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error("An error occurred during Google authentication: {}", e.getMessage());
                return new ResponseEntity<>("An error occurred during Google authentication", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.badRequest().body("AuthProvider not provided");
        }
    }

}
