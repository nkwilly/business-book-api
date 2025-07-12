package ink.yowyob.business.book.utils;

import ink.yowyob.business.book.domain.exception.AuthoritiesException;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.entity.UserRole;
import ink.yowyob.business.book.infrastructure.repository.RoleRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private UserRepository userRepository;


    public Optional<Authentication> getCurrentAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public Optional<String> getCurrentUsername() {
        return getCurrentAuthentication().map(Principal::getName);
    }

    public Set<GrantedAuthority> getCurrentAuthorities() {
        Optional<User> optionalUser = getCurrentUser();
        if (optionalUser.isPresent())
            return userRoleRepository.findByUserId(optionalUser.get().getId())
                    .map(UserRole::getRoleId)
                    .map(roleRepository::findById)
                    .publishOn(Schedulers.boundedElastic())
                    .map(role -> Objects.requireNonNull(role.block()).getName().name())
                    .collectList().block()
                    .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        throw new AuthoritiesException("User has not authorities yet");
    }

    public Optional<Object> getCurrentPrincipal() {
        return getCurrentAuthentication().map(Authentication::getPrincipal);
    }

    public boolean isAuthenticated() {
        return getCurrentAuthentication().isPresent();
    }

    public Optional<User> getCurrentUser() {
        String username = getCurrentUsername().orElse("");
        return userRepository.findByUsername(username).blockOptional();
    }

    public boolean isUserOfCurrentUser(String userId) {
        return getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId().equals(userId);
    }

    public boolean isCurrentUserAdmin() {
        return getCurrentAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","))
                .contains("ROLE_ADMIN");
    }
}

