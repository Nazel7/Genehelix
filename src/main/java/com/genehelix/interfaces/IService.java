package com.genehelix.interfaces;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.HcService;

import java.util.List;

public interface IService {
    List<String> getHcServiceNames(int cdId);

    List<HcServiceResponse> getHCServiceNameAndDate(int userID);

    void saveHcService(HcService service);

    List<HcService> getHCServiceListByCustomerId(int cId);
}
