package com.genehelix.repositories;

import com.genehelix.entities.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer> {

    CustomerDetails getCustomerDetailsByCustomerId(int cId);
}
