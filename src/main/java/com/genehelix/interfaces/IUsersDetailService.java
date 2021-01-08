package com.genehelix.interfaces;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.EmployeeDetails;

public interface IUsersDetailService {

    CustomerDetails saveUserDetails(CustomerDetails customerDetails);

    EmployeeDetails saveUserDetails(EmployeeDetails employeeDetails);

    CustomerDetails getUserDetailsById(int cdId);

    CustomerDetails getCustomerDetailsByCustomerId(int cId);


}
