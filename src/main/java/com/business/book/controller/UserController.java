package com.business.book.controller;

import com.business.book.entity.Token;
import com.business.book.entity.User;
import com.business.book.repository.TokenRepository;
import com.business.book.repository.UserRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(user);
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

        userRepository.delete(user);
        log.info("Deleted user with ID: {}", id);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    // Get user by token
    @GetMapping("/by-token")
    public ResponseEntity<SpecialUser> getUserByToken(@RequestParam String token) {
        // Find tokens that match the given token string
        List<Token> allTokens = tokenRepository.findAll();
        Optional<Token> foundToken = allTokens.stream()
                .filter(t -> t.getContent() != null && t.getContent().equals(token))
                .findFirst();

        if (foundToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }

        UUID userId = foundToken.get().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        SpecialUser specialUser = new SpecialUser(user);

        return ResponseEntity.ok(specialUser);
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
