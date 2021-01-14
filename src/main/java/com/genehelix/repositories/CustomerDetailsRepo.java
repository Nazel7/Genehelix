package com.genehelix.repositories;

import com.genehelix.entities.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer> {

    CustomerDetails getCustomerDetailsByCustomerId(int cId);

}
