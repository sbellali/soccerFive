package com.sbellali.soccerFive.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbellali.soccerFive.service.TokenService;
import com.sbellali.soccerFive.service.UserService;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final String PREFIX = "ROLE_";
    private final String BEARER_WORDING = "Bearer ";
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (isValidAuthHeader(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = extactJwt(authHeader);
        username = tokenService.extractUsername(jwt);

        if (username != null && !isUserAlreadyAuthenticated()) {
            authenticationByJwt(username, request);
        }
        filterChain.doFilter(request, response);

    }

    private String extactJwt(String authorization) {
        return authorization.substring(BEARER_WORDING.length());
    }

    private boolean isValidAuthHeader(String authHeader) {
        return (authHeader == null || !authHeader.startsWith(BEARER_WORDING));
    }

    private boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private Collection<SimpleGrantedAuthority> setPrefixRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(PREFIX + authority.getAuthority()))
                .collect(Collectors.toList());
    }

    private UsernamePasswordAuthenticationToken createAuthenticationByUserDetails(UserDetails user) {
        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                this.setPrefixRole(user.getAuthorities()));
    }

    private void authenticationByJwt(String username, HttpServletRequest request) {
        UserDetails userDetails = this.userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = createAuthenticationByUserDetails(userDetails);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}