package com.genehelix.services;

import com.genehelix.entities.User;
import com.genehelix.interfaces.IUserSevice;
import com.genehelix.repositories.UserRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements IUserSevice {

    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        user.setPassWord(Util.hashPassword(user.getPassWord()));
        return userRepo.save(user);
    }

    @Override
    public User findUserById(int id) {
        Optional<User> user= Optional.ofNullable(userRepo.findById(id));
         User user1;
         if(user.isPresent()){
             user1= user.get();
             System.out.println("CUstomerDEtailRepo" + user.get());
             return user1;
         }
        return null;
    }

}
