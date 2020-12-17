package com.genehelix.services;

import com.genehelix.interfaces.IUser;
import com.genehelix.entities.User;
import com.genehelix.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserDetailService implements UserDetails, UserDetailsService {
    protected User user;

    @Autowired
    private UserRepo userRepo;

    public UserDetailService() {
        super();
    }

    public UserDetailService(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    public String getActiveRole() {
        return user.getAuthority();
    }

    public IUser getActiveUser() {
        IUser customer = user.getCustomer();
        return customer != null ? customer : user.getEmployee();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUser(username);
        if (username == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new UserDetailService(user);
    }

    public User getUserByUserName(String username){

        return userRepo.findByUserName(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthority());
        try {
            return Arrays.asList(authority);
        } catch (Exception exception) {
            return null;
        }

    }

    @Override
    public String getPassword() {
        try {
            return user.getPassWord();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getUsername() {
        try {
            return user.getUserName();
        } catch (Exception exception) {
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
