package com.spring.boot.App2.springbootprojectwithdatarest.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority= new SimpleGrantedAuthority(user.getAuthority());
        try{
            return Arrays.asList(authority);
        }catch (Exception exception){
            return null;
        }

    }

    @Override
    public String getPassword() {
        try{
            return user.getPassWord();
        }catch (Exception exception){
            return null;
        }
    }

    @Override
    public String getUsername() {
        try{
            return user.getUserName();
        }catch (Exception exception){
            return null;
        }

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
