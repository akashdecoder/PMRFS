package com.api;

import com.api.model.Roles;
import com.api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetail implements UserDetails {

    private User user;

    public UserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUPassword();
    }

    @Override
    public String getUsername() {
        return user.getUEmail();
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

    public void setFirstName(String firstName) {
        this.user.setUFirstName(firstName);
    }

    public void setLastName(String lastName) {
        this.user.setULastName(lastName);
    }

    public boolean hasRole(String roleName) {
        return user.hasRole(roleName);
    }

    public String getFullName() {
        return user.getUFirstName() + " " + user.getULastName();
    }

}
