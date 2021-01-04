package com.genehelix.interfaces;

import com.genehelix.dtos.responses.HcServiceResponse;

import java.util.List;

public interface IService {
    List<String> getHcServiceNames(int cdId);

    List<HcServiceResponse> getHCServiceNameAndDate(int customerDetailID);
}
