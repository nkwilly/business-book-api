package com.business.book.infrastructure.security;

import com.business.book.infrastructure.repository.RoleRepository;
import com.business.book.infrastructure.repository.UserRepository;
import com.business.book.infrastructure.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)))
                .flatMap(user -> userRoleRepository.findByUserId(user.getId())
                        .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                        .collectList()
                        .map(authorities -> UserDetailsImpl.build(user, authorities)));
    }
}