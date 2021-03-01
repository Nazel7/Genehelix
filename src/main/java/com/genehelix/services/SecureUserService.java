package com.genehelix.services;

import com.genehelix.entities.User;
import com.genehelix.interfaces.ISecureUserService;
import com.genehelix.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecureUserService implements ISecureUserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public void saveSecureUser(User user) {

        userRepo.save(user);

    }

    @Override
    public String getPasswordByCustomerId(int customerId) {


        return userRepo.getPasswordByCustomerId(customerId);
    }

    @Override
    public User getUserByCustomerId(int customerId) {

        return userRepo.getUserByCustomerId(customerId);
    }

    @Override
    public User getUserByEmployeeId(int id) {

        return userRepo.getUserByEmployeeId(id);
    }
}
