package com.myblog.myblog34.controller;

import com.myblog.myblog34.payload.LoginDto;
import com.myblog.myblog34.entity.Role;
import com.myblog.myblog34.entity.User;
import com.myblog.myblog34.payload.SignUpDto;
import com.myblog.myblog34.repository.RoleRepository;
import com.myblog.myblog34.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        logger.info("Attempting to authenticate user with username or email: {}", loginDto.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("User '{}' authenticated successfully", loginDto.getUsernameOrEmail());
        return ResponseEntity.ok("User signed in successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        logger.info("Attempting to register user with username: {}", signUpDto.getUsername());

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            logger.warn("Username '{}' is already taken", signUpDto.getUsername());
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            logger.warn("Email '{}' is already taken", signUpDto.getEmail());
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepository.findByName(signUpDto.getRoleType()).orElse(null);
        if (role == null) {
            logger.error("Role '{}' not found", signUpDto.getRoleType());
            return new ResponseEntity<>("Role not found", HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        logger.info("User '{}' registered successfully", signUpDto.getUsername());
        return new ResponseEntity<>("Registered successfully", HttpStatus.OK);
    }
}
