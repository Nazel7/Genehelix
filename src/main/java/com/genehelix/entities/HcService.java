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
    private String date;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "customerh_id")
    private Customer customerh;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "employeeh_id")
    private Employee employeeh;

    public HcService(){
        super();

    }

    public HcService(int id, String name, Customer customerh, Employee employeeh) {
        Id = id;
        this.name = name;
        this.customerh = customerh;
        this.employeeh= employeeh;
    }

    public HcService(int id, String name, String date, Customer customerh, Employee employeeh) {
        Id = id;
        this.name = name;
        this.date = date;
        this.customerh = customerh;
        this.employeeh = employeeh;
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
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomerh() {
        return customerh;
    }

    public void setCustomerh(Customer customerh) {
        this.customerh= customerh;
    }


    public Employee getEmployeeh() {
        return employeeh;
    }

    public void setEmployeeh(Employee employeeh) {
        this.employeeh = employeeh;
    }

    @Override
    public String toString() {
        return "HcService{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", Date='" + date + '\'' +
                ", customerDetails=" + customerh +
                ", employeeDetails=" + employeeh +
                '}';
    }
}
