package com.genehelix.interfaces;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.HcService;

import java.util.List;

public interface IHcService {
    List<String> getHcServiceNames(int cdId);

    List<HcServiceResponse> getHCServiceNameAndDate(int userID);

    void saveHcService(HcService service);

    List<HcService> getHCServiceListByCustomerId(int cId);

    List<HcServiceResponse> getHCServiceNameAndDateForEmployee(int employeeID);

    List<HcService> getHCServiceListByEmployeeId(int cId);

}
