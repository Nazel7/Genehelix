package com.genehelix.repositories;

import com.genehelix.entities.EmployeeProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeeProfilePhotoRepo extends JpaRepository<EmployeeProfilePhoto, Integer> {


    EmployeeProfilePhoto getEmployeeProfilePhotoByEmployeeId(int eId);


}
