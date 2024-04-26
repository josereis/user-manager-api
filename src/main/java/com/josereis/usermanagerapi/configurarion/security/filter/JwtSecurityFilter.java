package com.josereis.usermanagerapi.configurarion.security.filter;

import com.josereis.usermanagerapi.service.jwt.JwtService;
import com.josereis.usermanagerapi.service.user.UserService;
import com.josereis.usermanagerapi.shared.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.extractTokenFromRequest(request);
        try {
            if (this.jwtService.validateToken(token)) {
                String username = jwtService.getUsername(token);
                UserDetails user = userService.loadUserByUsername(username);

                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            SecurityContextHolder.clearContext();
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }

        return null;
    }
}
