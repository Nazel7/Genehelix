package com.genehelix.entities;

import com.genehelix.interfaces.IUserDetail;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "customer_details")
@Access(AccessType.FIELD)
public class CustomerDetails implements IUserDetail {
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerd_id")
    private Customer customer;

    public CustomerDetails(){
        super();
    }

    public CustomerDetails(int Id, String twitter, String linkedin, String homeAddress,
                           String mobile, String occupation, String dateTime) {
        this.Id= Id;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.homeAddress = homeAddress;
        this.mobile = mobile;
        this.occupation = occupation;
        this.dateTime = dateTime;
    }

    public CustomerDetails(int id, String twitter, String linkedin, String homeAddress, String mobile,
                           String occupation, String dateTime,
                           Customer customer) {
        Id = id;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.homeAddress = homeAddress;
        this.mobile = mobile;
        this.occupation = occupation;
        this.dateTime = dateTime;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "Id=" + Id +
                ", twitter='" + twitter + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", mobile='" + mobile + '\'' +
                ", occupation='" + occupation + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", customer=" + customer +
                '}';
    }
}
