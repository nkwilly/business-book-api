package ink.yowyob.business.book.infrastructure.security;

import ink.yowyob.business.book.infrastructure.entity.Role;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.entity.UserRole;
import ink.yowyob.business.book.infrastructure.repository.RoleRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).block();
        if (user == null)
            throw new UsernameNotFoundException("username " + username + " not found");
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId()).collectList().block();
        if (userRoles == null)
            throw new UsernameNotFoundException("userRoles not found exception");
        List<Role> authorities = userRoles.stream().map(userRole -> roleRepository
                .findById(userRole.getRoleId())).map(Mono::block).toList();
        return UserDetailsImpl.build(user, authorities);
    }
}