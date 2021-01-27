package com.genehelix.services;

import com.genehelix.entities.CustomerDetails;
import com.genehelix.entities.EmployeeDetails;
import com.genehelix.interfaces.IUserDetail;
import com.genehelix.interfaces.IUsersDetailService;
import com.genehelix.repositories.CustomerDetailsRepo;
import com.genehelix.repositories.EmployeeDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersDetailService implements IUsersDetailService {

    @Autowired
    CustomerDetailsRepo customerDetailsRepo;

    @Autowired
    EmployeeDetailRepo employeeDetailsRepo;

    @Override
    public void   saveUserDetails(CustomerDetails customerDetails) {

        customerDetailsRepo.save(customerDetails);


    }

    @Override
    public void saveUserDetails(EmployeeDetails employeeDetails) {
                 employeeDetailsRepo.save(employeeDetails);
    }

    @Override
    public CustomerDetails getUserDetailsById(int cdId) {
        Optional<CustomerDetails> customerDetails = customerDetailsRepo.findById(cdId);
        if(customerDetails.isPresent()){
            CustomerDetails customerDetails1= customerDetails.get();
            return customerDetails1;
        }
        return null;
    }

    @Override
    public CustomerDetails getCustomerDetailsByCustomerId(int cId) {
        CustomerDetails customerDetails= customerDetailsRepo.getCustomerDetailsByCustomerId(cId);

        return customerDetails;
    }

}
