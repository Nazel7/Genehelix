package com.spring.boot.App2.springbootprojectwithdatarest.appServiceDAO;


import com.spring.boot.App2.springbootprojectwithdatarest.appDAO.CustomerRepo;
import com.spring.boot.App2.springbootprojectwithdatarest.appDAO.EmployeeRepository;
import com.spring.boot.App2.springbootprojectwithdatarest.appDAO.ReviewRepo;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Customer;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Employee;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDAOImpl implements EmployeeServiceDAO {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public List<Employee> getEmployees() {
       return repository.findAllByOrderByIdDesc();
    }

    @Override
    public Employee getEmployee(int empID) {
        Optional<Employee> employee = repository.findById(empID);
        Employee employee1;
        if(employee.isPresent()){
            employee1= employee.get();
            return employee1;
        }else {
            return null;
        }

    }

    @Override
    @Transactional
    public void deleteEmployee(int empID) {
        if(empID > 0){
            repository.deleteById(empID);
        }else{
            throw new RuntimeException("Exception thrown Employee ID: " + empID+ " not found");
        }

    }

    @Override
    public void addEmployee(Employee employee) {
     repository.save(employee);
    }

      @Override
    public List<Employee> searchEmployee(String employee){
            if(employee.trim().length() > 0){
                return repository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(employee, employee);
            }else{
                return getEmployees();
            }
    }

    @Override
    public List<String> showReviews(int employeeID) {
    return repository.getReviews(employeeID);

    }

    @Override
    public List<Customer> getEmployeeCustomerList(int employeeID) {

        return repository.getCustomer(employeeID);
    }


    @Override
    public List<String> employeeCustomerReview(int customerID) {

     return  customerRepo.getEmployeeCustomerReviews(customerID);


    }

    @Override
    public List<Customer> searchEmployeeCustomer(String employee, int employeeId) {
        if(employee.trim().length() <= 0 ){
            return getEmployeeCustomerList(employeeId);
        }
        return customerRepo.getSearchCustomers(employee, employeeId);
    }

    @Override
    public void addEmployeeCustomer(Customer customer) {
        customerRepo.save(customer);
    }



    @Override
    public Customer getCustomerById(int customerId) {
        Optional<Customer> customer= customerRepo.findById(customerId);
        Customer customer1;
        if(customer.isPresent()){
          customer1  =customer.get();
          return customer1;
        }else{
        return null;
        }
    }

    @Override
    public void deleteEmployeeCustomer(int employeeCustomerId) {
        customerRepo.deleteById(employeeCustomerId);
    }

    @Override
    public List<String> showCustomerReviewList(int customerId) {

        return customerRepo.getCustomerReviews(customerId);
    }

    @Override
    public void addNewReview(Review review) {
        reviewRepo.save(review);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
       return repository.findAll(pageable);

    }

    @Override
    public Page<Customer> findPaginatedCustomer(int pageNo, int pageSize, int employeeId) {
        Pageable pageable= PageRequest.of(pageNo-1, pageSize);
       return repository.getPaginatedCustomer(employeeId, pageable);

    }

    @Override
    public Page<Employee> getSearchPaginatedEmployeeHome(String entityProperty, int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo- 1, pageSize);
     Page<Employee>  employeePage= repository.getSearchPaginatedEmployeeHome(entityProperty, pageable);
        return employeePage;
    }

    @Override
    public List<Customer> getEmployeeCustomers(String employeeName) {

        return repository.getEmployeeCustomers(employeeName);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepo.findAllByOrderByIdDesc();
    }

    @Override
    public Page<Customer> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1, pageSize);

        return customerRepo.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Page<Customer> getAllCustomers(String customerProperty, int pageNo, int pageSize) {
       Pageable pageable= PageRequest.of(pageNo-1,pageSize );
        return customerRepo.getSearchedCustomers(customerProperty, pageable);
    }

}
