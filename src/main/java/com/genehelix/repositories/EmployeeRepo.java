package com.genehelix.repositories;


import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    //    SearchAll orderly
    @Query("SELECT e from employee e order by e.id desc")
    List<Employee> findAllByOrderByIdDesc();

    //    search employees
    List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String employeeName, String Name);

    @Query("SELECT r.reviewMessage from review r inner JOIN " +
            "customer c on c.id = r.customer.id inner JOIN employee e on e.id = c.employee.id where e.id=?1 " +
            "order by r.id desc")
    List<String> getReviews(int employeeId);

    @Query("Select c from customer c inner join employee e on e.id= c.employee.id where e.id=?1 order by c.id desc")
    List<Customer> getCustomer(int employeeId);

    @Query("Select c from customer c inner join employee e on e.id= c.employee.id where e.id=?1 order by c.id desc")
    Page<Customer> getPaginatedCustomer(int employeeId, Pageable pageable);

    @Query("Select e from employee e where e.firstName like %?1% or e.lastName like %?1% or e.email like %?1% order by e.id desc")
    Page<Employee> getSearchPaginatedEmployeeHome(String entityProperty, Pageable pageable);

    @Query("Select c from customer c inner join employee e on e.id= c.employee.id " +
            "where e.firstName like %?1% or e.lastName like %?1% or e.email like %?1% order by c.id desc")
    List<Customer> getEmployeeCustomers(String employeeName);

    Employee getEmployeeByEmail(String email);
}
