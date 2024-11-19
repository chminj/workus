package com.example.workus.security;

import com.example.workus.user.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends LoginUser implements UserDetails {

    private Long no;
    private String id;
    private String password;
    private String name;
    private int roleNo;
    private Collection<?extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {

        this.no = user.getNo();
        this.id = user.getId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.roleNo = user.getRoleNo();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Long getNo() {
        return no;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public int getRoleNo() {
        return roleNo;
    }
}
