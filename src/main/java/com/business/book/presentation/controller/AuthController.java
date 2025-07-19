package com.business.book.presentation.controller;

import com.business.book.infrastructure.entity.Token;
import com.business.book.infrastructure.entity.User;
import com.business.book.infrastructure.entity.UserRole;
import com.business.book.infrastructure.entity.constants.Roles;
import com.business.book.infrastructure.repository.RoleRepository;
import com.business.book.infrastructure.repository.TokenRepository;
import com.business.book.infrastructure.repository.UserRepository;
import com.business.book.infrastructure.repository.UserRoleRepository;
import com.business.book.infrastructure.security.UserDetailsImpl;
import com.business.book.presentation.dto.JWTDto;
import com.business.book.presentation.dto.LoginDto;
import com.business.book.presentation.dto.RegisterDto;
import com.business.book.utils.GeneratorsUtils;
import com.business.book.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    private final ReactiveAuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    
    private final PasswordEncoder encoder;
    
    private final JwtUtils jwtUtils;
    
    private final RoleRepository roleRepository;
    
    private final UserRoleRepository userRoleRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public Mono<?> login(@RequestBody @Valid LoginDto loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        )
        .flatMap(auth -> {
            String token = jwtUtils.generateToken(auth);
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            return Mono.just(new JWTDto(
                    token, "Bearer", loginRequest.getUsername(), roles
                ));
        });
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegisterDto registerRequest) {
        return userRepository.existsByUsername(registerRequest.getUsername())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.just(ResponseEntity.badRequest().body("Username already exists"));
                    }
                    return userRepository.existsByEmail(registerRequest.getEmail());
                })
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.just(ResponseEntity.badRequest().body("Email already exists"));
                    }
                    return userRepository.existsByTel(registerRequest.getTel());
                })
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.just(ResponseEntity.badRequest().body("Phone number already exists"));
                    }

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

                    return roleRepository.findByName(Roles.ROLE_CUSTOMER.name())
                            .switchIfEmpty(Mono.error(new RuntimeException("Role ROLE_CUSTOMER not found")))
                            .flatMap(role ->
                                    userRepository.save(user)
                                            .flatMap(savedUser -> {
                                                UserRole userRole = new UserRole();
                                                userRole.setId(UUID.randomUUID());
                                                userRole.setUserId(savedUser.getId());
                                                userRole.setRoleId(role.getId());

                                                return userRoleRepository.save(userRole)
                                                        .doOnNext(savedUserRole -> log.info("registered user: {}", savedUser))
                                                        .map(savedUserRole -> ResponseEntity.ok("User registered successfully"));
                                            })
                            );
                })
                .onErrorResume(e -> {
                    log.error("Error during user registration: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(500).body("Registration failed"));
                });
    }
}
