package org.cb.minilms.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cb.minilms.config.security.service.JwtService;
import org.cb.minilms.dto.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(token);
        List<GrantedAuthority> authorities = jwtService.extractAuthorities(token);

        if (isAlreadyAuthenticated(username)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = new User(username, "", authorities);

        if (!jwtService.isTokenValid(token, userDetails)) {
            sendUnauthorizedResponse(response, "Token inválido o expirado");
            return;
        }

        if (!hasAnyRequiredRole(authorities, RoleType.ROLE_ADMIN,RoleType.ROLE_ADMIN)) {
            sendForbiddenResponse(response, "Acceso denegado: se requiere el rol ADMIN o USER");
            return;
        }

        setAuthenticationContext(token, username, authorities);
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isAlreadyAuthenticated(String username) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        return username == null || (currentAuth != null && currentAuth.isAuthenticated());
    }

    private boolean hasAnyRequiredRole(List<GrantedAuthority> authorities, RoleType... requiredRoles) {
        Set<String> requiredRoleNames = Arrays.stream(requiredRoles)
                .map(Enum::name)
                .collect(Collectors.toSet());

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(requiredRoleNames::contains);
    }

    private void setAuthenticationContext(String token, String username, List<GrantedAuthority> authorities) {
        Jwt jwt = Jwt.withTokenValue(token)
                .header("alg", "HS256")
                .claim("sub", username)
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        Authentication authentication = new JwtAuthenticationToken(jwt, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(message);
    }
}