package com.example.workus.security;

import com.example.workus.user.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends User implements UserDetails {

    private String username;
    private String password;
    private Collection<?extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.username = user.getId();
        this.password = user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
