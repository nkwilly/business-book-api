package com.business.book.utils;

import com.business.book.infrastructure.repository.RoleRepository;
import com.business.book.infrastructure.repository.UserRepository;
import com.business.book.infrastructure.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
