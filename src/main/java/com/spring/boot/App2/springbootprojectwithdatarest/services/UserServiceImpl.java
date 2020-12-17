package com.spring.boot.App2.springbootprojectwithdatarest.services;

import com.spring.boot.App2.springbootprojectwithdatarest.repositories.UserRepo;
import com.spring.boot.App2.springbootprojectwithdatarest.entities.MyUserDetails;
import com.spring.boot.App2.springbootprojectwithdatarest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("IAMHERE");
        User user = userRepo.getUser(username);
        if (username == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }

    public User getUserByUserName(String username){

        return userRepo.findByUserName(username);
    }
}
