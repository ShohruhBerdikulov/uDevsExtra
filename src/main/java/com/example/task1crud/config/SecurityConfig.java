package com.example.task1crud.config;

import com.example.task1crud.entity.RoleType;
import com.example.task1crud.security.JWTFilter;
import com.example.task1crud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final
    AuthService service;
    @Autowired
    JWTFilter jwtFilter;

    public SecurityConfig(@Lazy AuthService service) {
        this.service = service;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers( "/api/user").hasAnyAuthority(RoleType.ROLE_USER.name(),RoleType.ROLE_ADMIN.name())
                .antMatchers( "/api/user/**").hasAuthority(RoleType.ROLE_ADMIN.name())
                .antMatchers( "/api/task/withUserId/**").hasAuthority(RoleType.ROLE_ADMIN.name())
                .antMatchers( "/api/task/**").hasAnyAuthority(RoleType.ROLE_USER.name(),RoleType.ROLE_ADMIN.name());
//                .antMatchers(HttpMethod.GET, "/api/user").permitAll().anyRequest().authenticated();
//                .antMatchers(HttpMethod.PUT, "/api/user").permitAll().anyRequest().authenticated();
//                .antMatchers("/api/user/**").hasRole(RoleType.ROLE_ADMIN.name()).anyRequest().authenticated();
//                .antMatchers("/api/user/**").hasRole(RoleType.ROLE_ADMIN.name()).authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
