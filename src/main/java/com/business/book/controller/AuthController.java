package com.business.book.controller;

import com.business.book.entity.Role;
import com.business.book.entity.User;
import com.business.book.repository.UserRepository;
import com.business.book.security.UserDetailsImpl;
import com.business.book.service.constants.Roles;
import com.business.book.service.payload.JWTResponse;
import com.business.book.service.payload.LoginRequest;
import com.business.book.service.payload.RegisterRequest;
import com.business.book.service.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        log.info("{} logged", loginRequest.getUsername());
        return ResponseEntity.ok(new JWTResponse(
                token, "Bearer", loginRequest.getUsername(), roles
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            return ResponseEntity.badRequest().body("Username already exists");
        if (userRepository.existsByEmail(registerRequest.getEmail()))
            return ResponseEntity.badRequest().body("Email already exists");
        if (userRepository.existsByTel(registerRequest.getTel()))
            return ResponseEntity.badRequest().body("Phone number already exists");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setTel(registerRequest.getTel());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setCreatedBy(user.getId());
        user.setLastModifiedBy(user.getId());

        User registerUser = userRepository.save(user);
        log.info("registered user: {}", registerUser);
        return ResponseEntity.ok().body("User registered successfully");
    }
}
