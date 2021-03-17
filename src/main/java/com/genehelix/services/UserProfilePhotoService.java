package com.genehelix.services;

import com.genehelix.entities.*;
import com.genehelix.interfaces.IUserProfilePhotoService;
import com.genehelix.repositories.CustomerProfilePhotoRepo;
import com.genehelix.repositories.EmployeeeProfilePhotoRepo;
import com.genehelix.repositories.EmptyProfilePhotoRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfilePhotoService implements IUserProfilePhotoService {

    @Autowired
    CustomerProfilePhotoRepo customerProfilePhotoRepo;

    @Autowired
    EmptyProfilePhotoRepo emptyProfilePhotoRepo;

    @Autowired
    EmployeeeProfilePhotoRepo employeeProfilePhoto;

    @Override
    public void saveCustomerPorfilePhoto(MultipartFile file, CustomerProfilePhoto customerProfilePhoto,
                                         Customer customer) throws Exception{

        String fileName= Util.checkFileNameError(file);
         String fileFormat= Util.fileCovertToImageBase64String(file);

          customerProfilePhoto.setProfilePhoto(fileFormat);
          customerProfilePhoto.setPhotoName(fileName);
          customerProfilePhoto.setPhotoSize(file.getSize());
          customerProfilePhoto.setCustomer(customer);

          customerProfilePhotoRepo.save(customerProfilePhoto);
    }

    @Override
    public void saveEmployeePorfilePhoto(MultipartFile file, EmployeeProfilePhoto employeeProfilePhoto,
                                         Employee employee) throws Exception {

          String fileName= Util.checkFileNameError(file);
        String fileFormat= Util.fileCovertToImageBase64String(file);


       employeeProfilePhoto.setPhotoName(fileName);
        employeeProfilePhoto.setProfilePhoto(fileFormat);
       employeeProfilePhoto.setPhotoSize(file.getSize());
       employeeProfilePhoto.setEmployee(employee);

       this.employeeProfilePhoto.save(employeeProfilePhoto);

    }

    @Override
    public CustomerProfilePhoto getCustomerProfilePhotoByCustomerId(int cDId) {


        return customerProfilePhotoRepo.getCustomerProfilePhotoByCustomerId(cDId);
    }

    @Override
    public void saveEmptyProfilePhoto(EmptyProfilePhoto emptyProfilePhoto) {

        emptyProfilePhotoRepo.save(emptyProfilePhoto);
    }

    @Override
    public EmptyProfilePhoto getEmptyProfilePhotoById(int emtyId) {

        return emptyProfilePhotoRepo.getEmptyProfilePhotoById(emtyId);
    }

    @Override
    public EmployeeProfilePhoto getEmployeeProfilePhotoByEmployeeId(int eId) {

        return employeeProfilePhoto.getEmployeeProfilePhotoByEmployeeId(eId);

    }


}
