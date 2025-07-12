package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.infrastructure.entity.Token;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.repository.TokenRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.presentation.dto.user.SpecialUser;
import ink.yowyob.business.book.utils.GenerateUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
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
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Mono<User> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<User> createUser(@RequestBody User user) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()).block())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()).block())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByTel(user.getTel()).block())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }

        user.setId(GenerateUtils.generateId());
        return userRepository.save(user);
    }

    // Update a user
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Mono<User> updateUser(@PathVariable UUID id, @RequestBody SpecialUser userDetails) {
        User user = userRepository.findById(userDetails.getId()).block();

        if (user == null)
            throw new RuntimeException("User nor found");

        // Check if username, email or tel already exist and don't belong to the current user
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDetails.getUsername()).block()) && !user.getUsername().equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDetails.getEmail()).block()) && !user.getEmail().equals(userDetails.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByTel(userDetails.getTel()).block()) && !user.getTel().equals(userDetails.getTel())) {
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

        Mono<User> updatedUser = userRepository.save(user);
        log.info("Updated user: {}", updatedUser.block());
        return updatedUser;
    }

    // Delete a user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> deleteUser(@PathVariable UUID id) {
        Mono<User> user = userRepository.findById(id);

        if (user == null)
            throw new RuntimeException("User not found");

        return userRepository.delete(Objects.requireNonNull(user.block()));
    }

    // Get user by token
    @GetMapping("/by-token")
    public Mono<SpecialUser> getUserByToken(@RequestParam String token) {
        // Find tokens that match the given token string
        Flux<Token> allTokens = tokenRepository.findAll();
        Token foundToken = allTokens
                .filter(t -> t.getContent() != null && t.getContent().equals(token))
                .blockFirst();

        if (foundToken == null)
            throw new RuntimeException("No token found");

        UUID userId = foundToken.getUserId();
        User user = userRepository.findById(userId).block();

        if (user == null)
            throw new RuntimeException("User not found");

        SpecialUser specialUser = new SpecialUser(user);

        return Mono.just(specialUser);
    }
}
