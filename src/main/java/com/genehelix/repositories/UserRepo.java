package com.genehelix.repositories;

import com.genehelix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u where u.userName= ?1")
    User getUser(String username);

    User findByUserName(String username);

    User findById(int id);

    @Query("select p.passWord from User p inner join customer  c on c.id=p.customer.id where c.id=?1 ")
    String getPasswordByCustomerId(int customerId);

    User getUserByCustomerId(int customerId);

    User getUserByEmployeeId(int employeeId);

    @Query("select p.passWord from User p inner join employee  e on e.id=p.employee.id where e.id=?1 ")
    String getPasswordByEmployeeId(int eid);


}
