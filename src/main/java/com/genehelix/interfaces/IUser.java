package com.genehelix.interfaces;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.EmployeeDetails;

public interface IUser {
    int getId();
    String getFirstName();
    String getLastName();
    String getEmail();
}