package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.mapper.UserMapper;
import ink.yowyob.business.book.infrastructure.entity.Role;
import ink.yowyob.business.book.infrastructure.entity.Token;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.entity.UserRole;
import ink.yowyob.business.book.infrastructure.entity.constants.Roles;
import ink.yowyob.business.book.infrastructure.repository.RoleRepository;
import ink.yowyob.business.book.infrastructure.repository.TokenRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRoleRepository;
import ink.yowyob.business.book.infrastructure.security.UserDetailsImpl;
import ink.yowyob.business.book.presentation.dto.*;
import ink.yowyob.business.book.presentation.dto.ErrorDto;
import ink.yowyob.business.book.presentation.dto.user.LoginDto;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.utils.GenerateUtils;
import ink.yowyob.business.book.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final UserMapper userMapper;

    @PostMapping("/login")
    public Mono<?> login(@RequestBody @Valid LoginDto LoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(LoginDto.getUsername(), LoginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Token tokenEntity = new Token();
        tokenEntity.setId(GenerateUtils.generateId());
        tokenEntity.setUserId(userDetails.getId());
        tokenEntity.setContent(token);
        tokenEntity.setRoles(roles.stream().collect(Collectors.joining(" ")));
        tokenEntity.setSaveAt(Instant.now());
        tokenRepository.save(tokenEntity);
        JWTDto jwtDto = new JWTDto(
                token, "Bearer", userDetails.getUsername(), roles
        );
        return Mono.just(SuccessDto.of(HttpStatus.OK, "Login successful", jwtDto));
    }

    @PostMapping("/register")
    public Mono<?> register(@RequestBody @Valid RegisterDto registerDto) {
        Mono<Boolean> usernameCheck = userRepository.existsByUsername(registerDto.getUsername());
        //Mono<Boolean> emailCheck = userRepository.existsByEmail(registerDto.getEmail());
        //Mono<Boolean> phoneCheck = userRepository.existsByTel(registerDto.getTel());

        log.info("Checking if username exists: {}", registerDto.getUsername());
        log.info("Result : {}", usernameCheck.block());
        return Mono.empty();
        /*
        return Mono.zip(usernameCheck, emailCheck, phoneCheck)
                .flatMap(tuple -> {
                    Boolean usernameExists = tuple.getT1();
                    Boolean emailExists = tuple.getT2();
                    Boolean phoneExists = tuple.getT3();

                    if (usernameExists) {
                        return Mono.just(ErrorDto.of(400, "Username already exists"));
                    }
                    if (emailExists) {
                        return Mono.just(ErrorDto.of(400, "Email already exists"));
                    }
                    if (phoneExists) {
                        return Mono.just(ErrorDto.of(400, "Phone already exists"));
                    }

                    return processRegistration(registerDto);
                });*/
    }

    private Mono<Object> validateAndRegisterUser(RegisterDto registerDto) {
        return userRepository.existsByUsername(registerDto.getUsername())
                .flatMap(usernameExists -> {
                    if (Boolean.TRUE.equals(usernameExists)) {
                        return Mono.just(ErrorDto.of(400, "Username already exists"));
                    }

                    return userRepository.existsByEmail(registerDto.getEmail())
                            .flatMap(emailExists -> {
                                if (Boolean.TRUE.equals(emailExists)) {
                                    return Mono.just(ErrorDto.of(400, "Email already exists"));
                                }

                                return userRepository.existsByTel(registerDto.getTel())
                                        .flatMap(phoneExists -> {
                                            if (Boolean.TRUE.equals(phoneExists)) {
                                                return Mono.just(ErrorDto.of(400, "Phone already exists"));
                                            }

                                            // Toutes les vérifications ont réussi, procéder à l'enregistrement
                                            return processRegistration(registerDto);  // Cette méthode doit renvoyer un Mono
                                        });
                            });
                });
    }

    private Mono<?> processRegistration(RegisterDto dto) {
        User user = userMapper.toUser(dto);
        user.setPassword(encoder.encode(dto.getPassword()));

        Role role = roleRepository.findByName(Roles.ROLE_CUSTOMER.name());
        UserRole userRole = new UserRole();
        userRole.setId(GenerateUtils.generateId());
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());

        // Exécuter les deux opérations de sauvegarde et attendre qu'elles soient terminées
        return Mono.zip(
                userRepository.save(user),
                userRoleRepository.save(userRole)
        ).then(Mono.just(SuccessDto.of(201, "User registered successfully")));
    }

}
