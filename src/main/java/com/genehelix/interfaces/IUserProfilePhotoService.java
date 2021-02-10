package com.genehelix.interfaces;

import com.genehelix.entities.Customer;
import com.genehelix.entities.CustomerProfilePhoto;
import com.genehelix.entities.EmptyProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

public interface IUserProfilePhotoService{

    void saveCustomerPorfilePhoto(MultipartFile file, CustomerProfilePhoto customerProfilePhoto,
                                  Customer customer) throws Exception;

    CustomerProfilePhoto getCustomerProfilePhotoByCustomerId(int cDId);

    void saveEmptyProfilePhoto(EmptyProfilePhoto emptyProfilePhoto);

    EmptyProfilePhoto getEmptyProfilePhotoById(int emtyId);
}
