package com.spring.boot.App2.springbootprojectwithdatarest.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name = "enabled")
    private boolean tinyint;
    @Column(name = "authority")
    private String authority;

//    @Column(name = "customer_id")
//    @OneToOne
    @Transient
    private Customer customer = new Customer();

//    @Column(name = "employee_id")
//    @OneToOne
@Transient
    private Employee employee = new Employee();

    public User() {
        super();
    }

    public User(String userName, String passWord, boolean tinyint, String authority) {
        this.userName = userName;
        this.passWord = passWord;
        this.tinyint = tinyint;
        this.authority = authority;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isTinyint() {
        return tinyint;
    }

    public void setTinyint(boolean tinyint) {
        this.tinyint = tinyint;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return this.userName + "\n" +
                this.passWord + "\n" +
                this.tinyint + "\n" +
                this.authority;
    }
}
