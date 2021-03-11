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

import java.nio.file.NoSuchFileException;

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

        String fileName= Util.fileConvertToString(file);

        if(fileName.trim().isEmpty() || fileName.trim().contains(",") || fileName.trim().contains("..")){
            throw new NoSuchFileException("Not acceptable file format. file contains any of unacceptable char in filename." +
                    "Please edit filename");
        }
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

        String fileName= Util.fileConvertToString(file);

        if(fileName.trim().isEmpty() || fileName.trim().contains(",") || fileName.trim().contains("..")){
            throw new NoSuchFileException("Not acceptable file format. file contains any of unacceptable char in filename." +
                    "Please edit filename");
        }
        String fileFormat= Util.fileCovertToImageBase64String(file);

        employeeProfilePhoto.setProfilePhoto(fileFormat);
       employeeProfilePhoto.setPhotoName(fileName);
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
