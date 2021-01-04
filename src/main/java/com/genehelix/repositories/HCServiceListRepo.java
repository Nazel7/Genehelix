package com.genehelix.repositories;

import com.genehelix.entities.HCServiceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HCServiceListRepo extends JpaRepository<HCServiceList, Integer> {

    @Query("SELECT s.serviceTitle from HCServiceList s order by s.serviceTitle asc ")
    List<String> getHCServiceListName();


}
