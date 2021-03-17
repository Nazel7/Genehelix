package com.genehelix.repositories;

import com.genehelix.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {


    @Query("SELECT r.reviewMessage from review r inner JOIN " +
            "customer c on c.id = r.customer.id inner JOIN employee e on e.id = c.employee.id where c.id=?1 order by " +
            "r.id desc")
    List<String> getEmployeeCustomerReviews(int customerId);

    @Query("SELECT c from customer c where c.employee.id = ?2 and (c.firstName like %?1% or c.lastName like %?1%)")
    List<Customer> getSearchCustomers(String name, int employeeId);

    @Query("SELECT r.reviewMessage from review r where r.customer.id= ?1 order by r.id desc")
    List<String> getCustomerReviews(int customerId);

    List<Customer> findAllByOrderByIdDesc();

    @Query("SELECT c from customer c order by c.firstName asc")
    Page<Customer> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT c from customer c where c.firstName like %?1% or c.lastName like %?1% order by  c.id desc ")
    Page<Customer> getSearchedCustomers(String customerProperty, Pageable pageable);

    Customer getCustomerById(int cId);

}
