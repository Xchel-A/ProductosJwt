package com.srchicharron.productosjwt.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srchicharron.productosjwt.model.entity.Rol;
import com.srchicharron.productosjwt.model.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, boolean enabled,
                           Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol:usuario.getAuthorities()){
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }

        return new UserDetailsImpl(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(username, user.username);
    }
}
