package com.genehelix.services;


import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.entities.MedicalResult;
import com.genehelix.entities.Review;
import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.repositories.CustomerRepo;
import com.genehelix.repositories.EmployeeRepo;
import com.genehelix.repositories.MedicalResultRepo;
import com.genehelix.repositories.ReviewRepo;
import com.genehelix.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeCustomerService implements IEmployeeCustomerService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private MedicalResultRepo medicalResultRepo;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAllByOrderByIdDesc();
    }

    @Override
    public Employee getEmployee(int empID) {
        Optional<Employee> employee = employeeRepo.findById(empID);
        Employee employee1;

        if (employee.isPresent()) {
            employee1 = employee.get();
            return employee1;
        } else {
            return null;
        }

    }

    @Override
    public void deleteEmployee(int empID) {
        if (empID > 0) {
            employeeRepo.deleteById(empID);
        } else {
            throw new RuntimeException("Exception thrown Employee ID: " + empID + " not found");
        }

    }

    @Override
    public void addEmployee(Employee employee) {
       employeeRepo.save(employee);
    }

    @Override
    public List<Employee> searchEmployee(String employee) {
        if (employee.trim().length() > 0) {
            return employeeRepo.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(employee, employee);
        } else {
            return getEmployees();
        }
    }

    @Override
    public List<String> showReviews(int employeeID) {
        return employeeRepo.getReviews(employeeID);

    }

    @Override
    public List<Customer> getEmployeeCustomerList(int employeeID) {

        return employeeRepo.getCustomer(employeeID);
    }


    @Override
    public List<String> employeeCustomerReview(int customerID) {

        return customerRepo.getEmployeeCustomerReviews(customerID);


    }

    @Override
    public List<Customer> searchEmployeeCustomer(String employee, int employeeId) {
        if (employee.trim().length() <= 0) {
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
        Optional<Customer> customer = customerRepo.findById(customerId);

        return customer.orElse(null);
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
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return employeeRepo.findAll(pageable);

    }

    @Override
    public Page<Customer> findPaginatedCustomer(int pageNo, int pageSize, int employeeId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return employeeRepo.getPaginatedCustomer(employeeId, pageable);

    }

    @Override
    public Page<Employee> getSearchPaginatedEmployeeHome(String entityProperty, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Employee> employeePage = employeeRepo.getSearchPaginatedEmployeeHome(entityProperty, pageable);
        return employeePage;
    }

    @Override
    public List<Customer> getEmployeeCustomers(String employeeName) {

        return employeeRepo.getEmployeeCustomers(employeeName);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepo.findAllByOrderByIdDesc();
    }

    @Override
    public Page<Customer> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return customerRepo.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Page<Customer> getAllCustomers(String customerProperty, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return customerRepo.getSearchedCustomers(customerProperty, pageable);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {

        return employeeRepo.getEmployeeByEmail(email);
    }

    @Override
    public Page<MedicalResult> findAllByCustomerId(int cId, int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1, pageSize);

        return medicalResultRepo.findAllByCustomerId(cId, pageable);
    }

    @Override
    public List<MedicalResult> findMedicalResultsByCustomerId(int cId) {

        return medicalResultRepo.findMedicalResultsByCustomerIdOrderByMedicalResultIdDesc(cId);
    }

    @Override
    public void saveUserMedicalResult(MultipartFile file, MedicalResult medicalResult) throws IOException {

        String fileName= Util.checkFileNameError(file);

        medicalResult.setMedicalResult(Util.formatFileTOByteArray(file));
        medicalResult.setName(fileName);
        medicalResult.setSize(file.getSize());

        medicalResultRepo.save(medicalResult);

    }

    @Override
    public void deleteMedicalResult(MedicalResult medicalResult) {

        medicalResultRepo.delete(medicalResult);
    }

    @Override
    public MedicalResult findMedicalResultById(int mrId) {

        return medicalResultRepo.findById(mrId);
    }

    @Override
    public List<MedicalResult> findMedicalResultsByNameContainingAndCustomerId(String mrName, int customerId) {


        return medicalResultRepo.FindMedicalResultByNameAndCustomerId(mrName, customerId);
    }

}
