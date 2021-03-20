package com.genehelix.services;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.HcService;
import com.genehelix.interfaces.IHcService;
import com.genehelix.repositories.HcServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HcServiceService implements IHcService {
    @Autowired
    private HcServiceRepo hcServiceRepo;

    @Override
    public List<String> getHcServiceNames(int customerId) {
        List<String> hcServiceNames = hcServiceRepo.getHCServiceName(customerId);

        return hcServiceNames;
    }

    @Override
    public List<HcServiceResponse> getHCServiceNameAndDate(int customerID) {
        List<HcServiceResponse> hcServiceResponses = hcServiceRepo.getHCServiceNameAndDate(customerID);
        return hcServiceResponses;
    }

    @Override
    public void saveHcService(HcService service) {

        hcServiceRepo.save(service);
    }

    @Override
    public List<HcService> getHCServiceListByCustomerId(int cId) {

        return hcServiceRepo.getHCServiceListByCustomerID(cId);
    }

    @Override
    public List<HcServiceResponse> getHCServiceNameAndDateForEmployee(int employeeID) {

        return hcServiceRepo.getHCServiceNameAndDateForEmployee(employeeID);
    }

    @Override
    public List<HcService> getHCServiceListByEmployeeId(int eId) {


        return hcServiceRepo.getHCServiceListByEmployeeId(eId);
    }

    @Override
    public HcService getHcServiceById(int hcServiceId) {
        Optional<HcService> hcService = hcServiceRepo.findById(hcServiceId);

        return hcService.orElse(null);
    }
}