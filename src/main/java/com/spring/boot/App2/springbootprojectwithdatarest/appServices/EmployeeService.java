package com.spring.boot.App2.springbootprojectwithdatarest.appServices;


import com.spring.boot.App2.springbootprojectwithdatarest.entity.Customer;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Employee;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployee(int empID);

    void deleteEmployee(int empID);

    void addEmployee(Employee employee);

    List<Employee> searchEmployee(String employee);

    List<String> showReviews(int employeeID);

    List<Customer> getEmployeeCustomerList(int employeeID);

    List<String> employeeCustomerReview(int customerID);

    List<Customer> searchEmployeeCustomer(String employee, int employeeId);

    void addEmployeeCustomer(Customer customer);

    Customer getCustomerById(int customerId);

    void deleteEmployeeCustomer(int employeeCustomerId);

    List<String> showCustomerReviewList(int customerId);

    void addNewReview(Review review);

    Page<Employee> findPaginated(int pageNo, int pageSize);

    Page<Customer> findPaginatedCustomer(int pageNo, int pageSize, int employeeId);

    Page<Employee> getSearchPaginatedEmployeeHome(String entityProperty, int pageNo, int pageSize);

    List<Customer> getEmployeeCustomers(String employeeName);

    List<Customer> getAllCustomers();

    Page<Customer> getAllCustomers(int pageNo, int pageSize);

    Page<Customer> getAllCustomers(String customerProperty, int pageNo, int pageSize);

}