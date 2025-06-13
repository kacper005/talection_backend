package com.talection.talection.config;

import com.talection.talection.exception.JwtExpiredException;
import com.talection.talection.service.UserDetailsServiceImplementation;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    UserDetailsService userDetailsService;
    JwtUtil jwtUtil;

    private Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    /**
     * Create a JwtRequestFilter using a given JwtUtil and UserDetailsService.
     *
     * @param jwtUtil            the JwtUtil to use
     * @param userDetailsService the UserDetailsService to use
     */
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImplementation userDetailsService) {
        super();
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Register user as authenticated if the JWT token is valid and the user is not already
     * authenticated, then pass the HTTP request and response on to filter chain.
     *
     * @param request     the HTTP request to use in filtering
     * @param response    the HTTP response to use in filtering
     * @param filterChain the filter chain to pass the request and response to
     * @throws ServletException when a servlet error occurs
     * @throws IOException      when an IO error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String origin = request.getHeader("Origin");
        String requestType = request.getMethod();
        logger.trace("{} REQUEST FROM ORIGIN: {}", requestType, origin);
        String jwtToken = getJwt(request);

        try {
            String username = jwtToken != null ? getUsernameFromToken(jwtToken) : null;

            if (username != null && !isContextAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null && jwtUtil.validateToken(jwtToken, userDetails)) {
                    registerUserAsAuthenticated(request, userDetails);
                }
            }
        } catch (JwtExpiredException e) {
            logger.error("JWT token is expired");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Returns the jwt of a request, or null if there is none.
     *
     * @param request the request
     * @return the jwt of a request, or null if there is none.
     */
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.replace("Bearer ", "");
        }
        return jwt;
    }

    /**
     * Returns the username from a jwt.
     *
     * @param jwt the jwt
     * @return the username from the jwt
     */
    private String getUsernameFromToken(String jwt) {
        return jwtUtil.extractUsername(jwt);
    }

    /**
     * Returns true if the user is authenticated in the security context, and false otherwise.
     *
     * @return true if the user is authenticated in the security context, and false otherwise.
     */
    private boolean isContextAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    /**
     * Register a user as authenticated in the security context using a request and user details.
     *
     * @param request     the request
     * @param userDetails the user details
     */
    private static void registerUserAsAuthenticated(HttpServletRequest request,
                                                    UserDetails userDetails) {
        final UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);
    }
}
