package com.genehelix.repositories;

import com.genehelix.entities.CustomerProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerProfilePhotoRepo extends JpaRepository<CustomerProfilePhoto, Integer> {

    @Query("SELECT p from CustomerProfilePhoto p inner join customer c on c.id=p.customer.id where c.id=?1 ")
    CustomerProfilePhoto getCustomerProfilePhotoByCustomerId(int cDId);


}
