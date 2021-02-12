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
}
