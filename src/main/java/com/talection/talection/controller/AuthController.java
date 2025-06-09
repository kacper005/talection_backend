package com.talection.talection.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.talection.talection.config.GoogleTokenVerifier;
import com.talection.talection.config.JwtUtil;
import com.talection.talection.dto.AuthenticationRequest;
import com.talection.talection.enums.AuthProvider;
import com.talection.talection.service.UserDetailsServiceImplementation;
import jakarta.validation.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
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

    public AuthController(UserDetailsServiceImplementation userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {

        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<String> authenticate(AuthenticationRequest request) {
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
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            } catch (UsernameNotFoundException e) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } catch (Exception e) {
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
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.badRequest().body("AuthProvider not provided");
        }
    }

}
