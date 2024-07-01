package com.myblog.myblog34.controller;

import com.myblog.myblog34.entity.User;
import com.myblog.myblog34.payload.SignUpDto;
import com.myblog.myblog34.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        userRepository.save(user);

        logger.info("User '{}' registered successfully", signUpDto.getUsername());
        return new ResponseEntity<>("Registered successfully", HttpStatus.OK);
    }
}
