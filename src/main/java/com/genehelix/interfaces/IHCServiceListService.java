package com.genehelix.interfaces;

import com.genehelix.entities.HCServiceList;

import java.util.List;

public interface IHCServiceListService {

    List<String> getHCServiceListTitle();

    List<HCServiceList> findAll();
}
