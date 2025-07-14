package com.business.book.service.utils;

import com.business.book.entity.User;
import com.business.book.entity.UserRole;
import com.business.book.repository.RoleRepository;
import com.business.book.repository.UserRepository;
import com.business.book.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@AllArgsConstructor
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private UserRepository userRepository;

    /*
    public Mono<Authentication> getCurrentAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication);
    }

    public Mono<String> getCurrentUsername() {
        return getCurrentAuthentication()
                .map(Authentication::getName);
    }

    public Mono<Set<GrantedAuthority>> getCurrentAuthorities() {
        return getCurrentUser()
                .flatMapMany(user -> Flux.fromIterable(userRoleRepository.findByUserId(user.getId())))
                .flatMap(userRole -> userRole.getRoleId() != null ? 
                        Mono.justOrEmpty(roleRepository.findById(userRole.getRoleId())) : 
                        Mono.empty())
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collectSet()
                .switchIfEmpty(Mono.error(new RuntimeException("User has no authorities yet")));
    }

    public Mono<Object> getCurrentPrincipal() {
        return getCurrentAuthentication()
                .map(Authentication::getPrincipal);
    }

    public Mono<Boolean> isAuthenticated() {
        return getCurrentAuthentication()
                .map(auth -> true)
                .defaultIfEmpty(false);
    }

    public Mono<User> getCurrentUser() {
        return getCurrentUsername()
                .flatMap(userRepository::findByUsername);
    }

    public Mono<Boolean> isUserOfCurrentUser(String userId) {
        return getCurrentUser()
                .map(user -> user.getId().toString().equals(userId))
                .defaultIfEmpty(false);
    }

    public Mono<Boolean> isCurrentUserAdmin() {
        return getCurrentAuthorities()
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(authority -> authority.equals("ROLE_ADMIN")))
                .defaultIfEmpty(false);
    }

     */
}
