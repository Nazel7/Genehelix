package com.genehelix.entities;


import com.genehelix.interfaces.IUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "employee")
@Table(name = "employee")
@Access(AccessType.FIELD)
public class Employee implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 2, message = "at least one value is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 2, message = "at least one value is required")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 2, message = "at least one value is required")
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "employee")
    private EmployeeDetails employeeDetails;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employeeh")
    private List<HcService> service = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "employee")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Customer> customerList = new ArrayList<>();

    public Employee() {
        super();
    }

    public Employee(String firstName, String lastName, String email, List<Customer> customerList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.customerList = customerList;
    }

    public Employee(int id, String firstName, String lastName, String email, List<Customer> customerList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.customerList = customerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String company) {
        this.email = company;
    }

    public EmployeeDetails getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    @Override
    public CustomerDetails getCustomerDetails() {
        return null;
    }

    @Override
    public void setCustomerDetails(CustomerDetails customerDetails) {

    }

    public List<HcService> getService() {
        return service;
    }

    public void setService(List<HcService> service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }


    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", employeeDetails=" + employeeDetails +
                ", service=" + service +
                ", user=" + user +
                ", customerList=" + customerList +
                '}';
    }
}
