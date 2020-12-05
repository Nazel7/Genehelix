package com.spring.boot.App2.springbootprojectwithdatarest.employeeServiceDAO;

import com.spring.boot.App2.springbootprojectwithdatarest.employeeDAO.UserRepopository;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.MyUserDetails;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepopository userRepopository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepopository.getUser(username);
        if(username == null){
            throw new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }
}
