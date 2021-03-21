package com.genehelix.repositories;

import com.genehelix.dtos.responses.HcServiceResponse;
import com.genehelix.entities.HcService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HcServiceRepo extends JpaRepository<HcService, Integer> {

    @Query("SELECT s.name from HcService s inner join customer c on c.id=s.customerh.id" +
            " where c.id= ?1 order by s.Id")
    List<String> getHCServiceName(int customerID);


    @Query("SELECT new com.genehelix.dtos.responses.HcServiceResponse(s.Id, s.name, s.date) " +
            "from HcService s inner join customer c on c.id=s.customerh.id" +
            " where c.id= ?1 order by s.Id")
    List<HcServiceResponse> getHCServiceNameAndDate(int customerID);

    @Query("SELECT hc from HcService hc inner join customer c on c.id=hc.customerh.id where c.id=?1 order by hc.Id desc")
    List<HcService> getHCServiceListByCustomerID(int cId);


    @Query("SELECT new com.genehelix.dtos.responses.HcServiceResponse(s.Id,s.name, s.date) " +
            "from HcService s inner join employee e on e.id=s.customerh.id" +
            " where e.id= ?1 order by s.Id")
    List<HcServiceResponse> getHCServiceNameAndDateForEmployee(int employeeID);

    @Query("SELECT hc from HcService hc inner join employee e on e.id=hc.employeeh.id where e.id=?1 order by hc.Id desc")
    List<HcService> getHCServiceListByEmployeeId(int cId);

    @Query("SELECT hc FROM HcService hc where hc.employeeh.id=?2 and (hc.name like %?1%)")
    List<HcService> getHcServicesByNameContainingAndEmployeehId(String hcName, int eId);

    @Query("SELECT hc FROM HcService hc where hc.customerh.id=?2 and  (hc.name like %?1%)")
    List<HcService> getHcServicesByNameContainingAndCustomerhId(String hcName, int cId);

}
