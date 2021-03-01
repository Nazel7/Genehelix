package com.genehelix.interfaces;

import com.genehelix.entities.User;

public interface ISecureUserService {

    void saveSecureUser(User user);

    String getPasswordByCustomerId(int customerId);

    User getUserByCustomerId(int customerId);

    User getUserByEmployeeId(int id);
}
