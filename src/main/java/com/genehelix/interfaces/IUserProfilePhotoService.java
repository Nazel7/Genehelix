package com.genehelix.interfaces;

import com.genehelix.entities.*;
import org.springframework.web.multipart.MultipartFile;

public interface IUserProfilePhotoService{

    void saveCustomerPorfilePhoto(MultipartFile file, CustomerProfilePhoto customerProfilePhoto,
                                  Customer customer) throws Exception;

    void saveEmployeePorfilePhoto(MultipartFile file, EmployeeProfilePhoto employeeProfilePhoto,
                                  Employee employee) throws Exception;

    CustomerProfilePhoto getCustomerProfilePhotoByCustomerId(int cDId);

    void saveEmptyProfilePhoto(EmptyProfilePhoto emptyProfilePhoto);

    EmptyProfilePhoto getEmptyProfilePhotoById(int emtyId);

    EmployeeProfilePhoto getEmployeeProfilePhotoByEmployeeId(int eId);
}
