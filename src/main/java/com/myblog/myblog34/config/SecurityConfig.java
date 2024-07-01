package com.myblog.myblog34.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Define a bean for password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security settings for HTTP requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection
                .authorizeRequests() // Authorize requests
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()// Allow all GET requests to /api/** without authentication
                .anyRequest().authenticated() // Require authentication for any other request
                .and()
                .httpBasic(); // Enable HTTP Basic authentication
    }

    // Define a UserDetailsService bean with in-memory user details
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // Create a user with username "sharath" and password "password", and assign the role "USER"
        UserDetails user1 = User.builder()
                .username("sharath")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        // Use InMemoryUserDetailsManager to manage the user details
        return new InMemoryUserDetailsManager(user1,user2);
    }
}
