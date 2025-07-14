package com.business.book.controller;

import com.business.book.entity.Role;
import com.business.book.entity.Token;
import com.business.book.entity.User;
import com.business.book.entity.UserRole;
import com.business.book.entity.constants.Roles;
import com.business.book.repository.RoleRepository;
import com.business.book.repository.TokenRepository;
import com.business.book.repository.UserRepository;
import com.business.book.repository.UserRoleRepository;
import com.business.book.security.UserDetailsImpl;
import com.business.book.service.payload.response.JWTResponse;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;
import com.business.book.service.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    
    private final PasswordEncoder encoder;
    
    private final JwtUtils jwtUtils;
    
    private final RoleRepository roleRepository;
    
    private final UserRoleRepository userRoleRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Test 0");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        log.info("Test 1");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Test 2");

        String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        log.info("{} logged", loginRequest.getUsername());

        Token tokenEntity = new Token();
        tokenEntity.setId(UUID.randomUUID());
        tokenEntity.setUserId(userDetails.getId());
        tokenEntity.setContent(token);
        tokenEntity.setRoles(roles.stream().collect(Collectors.joining(" ")));
        tokenEntity.setSaveAt(Instant.now());
        tokenRepository.save(tokenEntity);

        return ResponseEntity.ok(new JWTResponse(
                token, "Bearer", loginRequest.getUsername(), roles
        ));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(@RequestBody @Valid RegisterRequest registerRequest) {
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
                            .flatMap(role -> {
                                UserRole userRole = new UserRole();
                                userRole.setId(UUID.randomUUID());
                                userRole.setUserId(user.getId());
                                userRole.setRoleId(role.getId());

                                return userRoleRepository.save(userRole)
                                        .then(userRepository.save(user))
                                        .doOnNext(savedUser -> log.info("registered user: {}", savedUser))
                                        .map(savedUser -> ResponseEntity.ok().body("User registered successfully"));
                            });
                });
    }
}
