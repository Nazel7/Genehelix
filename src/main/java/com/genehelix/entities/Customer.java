package com.genehelix.entities;

import com.genehelix.interfaces.IUser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "customer")
@Table(name = "customer")
@Access(AccessType.FIELD)
public class Customer implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = " at least 2 character is required")
    @Size(min = 2, message = "at least one value is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "at least 1 character is required")
    @Size(min = 2, message = "at least one value is required")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "at least 11 character is required")
    @Size(min = 11, message = "at least 11 character is required")
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private CustomerProfilePhoto customerProfilePhoto;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade= CascadeType.ALL, mappedBy = "customer")
    private CustomerDetails customerDetails;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "customer")
    private UserResume userResume;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customerh")
    private List<HcService> service = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "customer")
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<MedicalResult> medicalResults= new ArrayList<>();

    public Customer() {
        super();
    }

    public Customer(String firstName, String lastName, List<Review> reviewList, Employee employee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reviewList = reviewList;
        this.employee = employee;
    }

    public Customer(String firstName, String lastName, List<Review> reviewList, CustomerDetails customerDetails,
                    CustomerProfilePhoto customerProfilePhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reviewList = reviewList;
        this.customerDetails= customerDetails;
        this.customerProfilePhoto= customerProfilePhoto;
    }

    public Customer(int id, UserResume userResume) {
        this.id = id;
        this.userResume = userResume;
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

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerProfilePhoto getCustomerProfilePhoto() {
        return customerProfilePhoto;
    }

    public void setCustomerProfilePhoto(CustomerProfilePhoto customerProfilePhoto) {
        this.customerProfilePhoto = customerProfilePhoto;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void addReview(Review review) {
        reviewList.add(review);
    }

    public Employee getEmployee() {
        return employee;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public UserResume getUserResume() {
        return userResume;
    }

    public void setUserResume(UserResume userResume) {
        this.userResume = userResume;
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<MedicalResult> getMedicalResults() {
        return medicalResults;
    }

    public void setMedicalResults(List<MedicalResult> medicalResults) {
        this.medicalResults = medicalResults;
    }


}