package com.genehelix.repositories;

import com.genehelix.entities.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepo extends JpaRepository<EmployeeDetails, Integer> {

    EmployeeDetails getEmployeeDetailsByEmployeeId(int eId);

}
