package com.business.book.security;

import com.business.book.entity.Role;
import com.business.book.entity.User;
import com.business.book.entity.UserRole;
import com.business.book.repository.RoleRepository;
import com.business.book.repository.UserRepository;
import com.business.book.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        List<Role> authorities = userRoles.stream().map(userRole -> roleRepository
                .findById(userRole.getRoleId())).filter(Optional::isPresent).map(Optional::get).toList();
        return UserDetailsImpl.build(user, authorities);
    }
}