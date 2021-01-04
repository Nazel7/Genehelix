package com.genehelix.repositories;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.HcService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HcServiceRepo extends JpaRepository<HcService, Integer> {

    @Query("SELECT s.name from HcService s inner join CustomerDetails c on c.Id=s.customerDetails.Id" +
            " where c.Id= ?1 order by s.Id")
    List<String> getHCServiceName(int customerDetailID);


    @Query("SELECT new com.genehelix.dtos.responses.HcServiceResponse(s.name, s.Date) " +
            "from HcService s inner join CustomerDetails c on c.Id=s.customerDetails.Id" +
            " where c.Id= ?1 order by s.Id")
    List<HcServiceResponse> getHCServiceNameAndDate(int customerDetailID);

}
