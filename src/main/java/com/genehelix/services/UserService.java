package com.genehelix.services;

import com.genehelix.entities.User;
import com.genehelix.interfaces.IUserSevice;
import com.genehelix.repositories.UserRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserSevice {

    @Autowired
    protected UserRepo userRepo;

    public User createUser(User user) {
        user.setPassWord(Util.hashPassword(user.getPassWord()));
        return userRepo.save(user);
    }
}
