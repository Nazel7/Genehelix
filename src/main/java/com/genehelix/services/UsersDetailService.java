package com.genehelix.services;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.EmployeeDetails;
import com.genehelix.interfaces.IUserDetail;
import com.genehelix.interfaces.IUsersDetailService;
import com.genehelix.repositories.CustomerDetailsRepo;
import com.genehelix.repositories.EmployeeDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailService implements IUsersDetailService {

    @Autowired
    CustomerDetailsRepo customerDetailsRepo;

    @Autowired
    EmployeeDetailRepo employeeDetailsRepo;

    @Override
    public CustomerDetails saveUserDetails(CustomerDetails customerDetails) {
             if(customerDetails != null){
              return    customerDetailsRepo.save(customerDetails);
             }
      return null;
    }

    @Override
    public EmployeeDetails saveUserDetails(EmployeeDetails employeeDetails) {
        if(employeeDetails != null){
            return employeeDetailsRepo.save(employeeDetails);
        }
        return null;
    }

}
