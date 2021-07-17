package com.example.task1crud.security;

import com.example.task1crud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    AuthService service;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String substring = authorization.substring(7);
            String user = jwtProvider.getUserNameFromToken(substring);
            if (user != null) {
                UserDetails userDetails = service.loadUserByUsername(user);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
