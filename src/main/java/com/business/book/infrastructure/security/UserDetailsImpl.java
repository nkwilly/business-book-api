package com.business.book.infrastructure.security;

import com.business.book.infrastructure.entity.Role;
import com.business.book.infrastructure.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;

    private final String username;

    private final String email;

    private final String password;

    private final String tel;

    Collection<? extends GrantedAuthority> authorities;
    
    public UserDetailsImpl(UUID id, String username, String email,
                           String password, String tel, Collection<? extends GrantedAuthority> authorities ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user, List<Role> roles) {
        List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl (user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getTel(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}