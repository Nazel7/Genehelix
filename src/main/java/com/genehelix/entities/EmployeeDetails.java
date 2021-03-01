package com.genehelix.entities;

import com.genehelix.interfaces.IUserDetail;

import javax.persistence.*;

@Entity
@Table(name= "employee_details")
@Access(AccessType.FIELD)
public class EmployeeDetails implements IUserDetail {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name= "home_address")
    private String homeAddress;

    @Column(name= "mobile")
    private String mobile;

    @Column(name= "occupation")
    private String occupation;

    @Column(name= "date_time")
    private String dateTime;


    @OneToOne(cascade ={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH} )
    @JoinColumn(name = "employeed_id")
    private Employee employee;

    public EmployeeDetails(){
        super();
    }

    public EmployeeDetails(int Id, String twitter, String linkedin, String homeAddress,
                           String mobile, String occupation, String dateTime) {
        this.Id= Id;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.homeAddress = homeAddress;
        this.mobile = mobile;
        this.occupation = occupation;
        this.dateTime = dateTime;
    }

    public EmployeeDetails(int id, String twitter, String linkedin, String homeAddress, String mobile,
                           String occupation, String dateTime, Employee employee) {
        Id = id;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.homeAddress = homeAddress;
        this.mobile = mobile;
        this.occupation = occupation;
        this.dateTime = dateTime;
        this.employee = employee;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
