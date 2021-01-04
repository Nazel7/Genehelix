package com.genehelix.services;

import com.genehelix.interfaces.IHCServiceListService;
import com.genehelix.repositories.HCServiceListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HCServiceListService implements IHCServiceListService {

    @Autowired
    private HCServiceListRepo hcServiceListRepo;


    @Override
    public List<String> getHCServiceList() {

        return hcServiceListRepo.getHCServiceListName();
    }
}
