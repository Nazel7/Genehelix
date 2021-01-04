package com.genehelix.services;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.interfaces.IService;
import com.genehelix.repositories.HcServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HcServiceService implements IService {
    @Autowired
    private HcServiceRepo hcServiceRepo;

    @Override
    public List<String> getHcServiceNames(int cdId) {
        List<String> hcServiceNames= hcServiceRepo.getHCServiceName(cdId);

        return hcServiceNames;
    }

    @Override
    public List<HcServiceResponse> getHCServiceNameAndDate(int customerDetailID) {
          List<HcServiceResponse> hcServiceResponses= hcServiceRepo.getHCServiceNameAndDate(customerDetailID);
        return hcServiceResponses;
    }
}