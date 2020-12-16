package com.spring.boot.App2.springbootprojectwithdatarest.appServices;

import com.spring.boot.App2.springbootprojectwithdatarest.appRepositories.UserRepopository;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.MyUserDetails;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepopository userRepopository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("IAMHERE");
        User user = userRepopository.getUser(username);
        if (username == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }

    public User getUserByUserName(String username){

        return userRepopository.findByUserName(username);
    }
}
