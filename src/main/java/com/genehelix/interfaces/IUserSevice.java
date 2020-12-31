package com.genehelix.interfaces;

import com.genehelix.entities.User;

public interface IUserSevice {
    User createUser(User user);

    User findUserById(int id);
}
