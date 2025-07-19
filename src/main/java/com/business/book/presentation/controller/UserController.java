package com.business.book.presentation.controller;

import com.business.book.infrastructure.repository.TokenRepository;
import com.business.book.infrastructure.repository.UserRepository;
import com.business.book.utils.JwtUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.business.book.infrastructure.entity.User;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /*
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (userRepository.existsByTel(user.getTel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }

        // Generate new UUID
        user.setId(UUID.randomUUID());
        User savedUser = userRepository.save(user);
        log.info("Created user: {}", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Update a user
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody SpecialUser userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if username, email or tel already exist and don't belong to the current user
        if (userRepository.existsByUsername(userDetails.getUsername()) && !user.getUsername().equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(userDetails.getEmail()) && !user.getEmail().equals(userDetails.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (userRepository.existsByTel(userDetails.getTel()) && !user.getTel().equals(userDetails.getTel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }

        // Update user fields
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setTel(userDetails.getTel());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            String pass = passwordEncoder.encode(userDetails.getPassword());
            user.setPassword(pass);
        } else user.setPassword(user.getPassword());

        User updatedUser = userRepository.save(user);
        log.info("Updated user: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
*/
    // Get user by token
    @GetMapping("/by-token/{token}")
    public Mono<SpecialUser> getUserByToken(@PathVariable String token) {
        String username = jwtUtils.getUsernameFromToken(token);

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .map(SpecialUser::new)
                .doOnNext(user -> log.info("Retrieved user by token: {}", user.getUsername()))
                .onErrorResume(e -> {
                    log.error("Error retrieving user by token: {}", e.getMessage());
                    return Mono.error(e);
                });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class SpecialUser {
        private UUID id;
        private String username;
        private String email;
        private String tel;
        private String password;

        public SpecialUser(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.tel = user.getTel();
            this.password = user.getPassword();
        }
    }

}
