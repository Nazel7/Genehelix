package com.genehelix.interfaces;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.EmployeeDetails;

public interface IUsersDetailService {

  void saveUserDetails(CustomerDetails customerDetails);

  void  saveUserDetails(EmployeeDetails employeeDetails);

    CustomerDetails getUserDetailsById(int cdId);

    CustomerDetails getCustomerDetailsByCustomerId(int cId);

  EmployeeDetails getEmployeeDetailsByEmployeeId(int eId);


}
