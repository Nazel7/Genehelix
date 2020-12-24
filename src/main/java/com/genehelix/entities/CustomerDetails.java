package com.genehelix.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "customer_details")
@Access(AccessType.FIELD)
public class CustomerDetails {
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

    private String nullValue= "NO DATA";

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name= "service_key")
    private List<HcService> serviceKey= new ArrayList<>();

    @Column(name= "date_time")
    private String dateTime;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "customerDetails")
    private User user;


    public CustomerDetails(){
        super();
    }

    public CustomerDetails(int Id, String twitter, String linkedin, String homeAddress,
                           String mobile, String occupation, List<HcService> serviceKey, String dateTime) {
        this.Id= Id;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.homeAddress = homeAddress;
        this.mobile = mobile;
        this.occupation = occupation;
        this.serviceKey = serviceKey;
        this.dateTime = dateTime;
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

    public List<HcService> getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(List<HcService> serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void addService(HcService service){
        serviceKey.add(service);

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
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
                ", serviceKey=" + serviceKey +
                ", dateTime='" + dateTime + '\'' +
                ", User=" + user +
        '}';
    }
}
