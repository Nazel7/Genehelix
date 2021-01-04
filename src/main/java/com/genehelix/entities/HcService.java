package com.genehelix.entities;

import javax.persistence.*;

@Entity
@Table(name= "hc_service")
@Access(AccessType.FIELD)
public class HcService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int Id;

    @Column(name= "name")
    private String name;

    @Column(name = "date_time")
    private String Date;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "customerdetails_id")
    private CustomerDetails customerDetails;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "employeedetails_id")
    private EmployeeDetails employeeDetails;

    public HcService(){
        super();

    }

    public HcService(int id, String name, CustomerDetails customerDetails, EmployeeDetails employeeDetails) {
        Id = id;
        this.name = name;
        this.customerDetails = customerDetails;
        this.employeeDetails= employeeDetails;
    }

    public HcService(int id, String name, String date, CustomerDetails customerDetails, EmployeeDetails employeeDetails) {
        Id = id;
        this.name = name;
        Date = date;
        this.customerDetails = customerDetails;
        this.employeeDetails = employeeDetails;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public EmployeeDetails getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    @Override
    public String toString() {
        return "HcService{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", Date='" + Date + '\'' +
                ", customerDetails=" + customerDetails +
                ", employeeDetails=" + employeeDetails +
                '}';
    }
}
