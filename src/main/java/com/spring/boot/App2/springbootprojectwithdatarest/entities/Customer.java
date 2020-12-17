package com.spring.boot.App2.springbootprojectwithdatarest.entities;

import com.spring.boot.App2.springbootprojectwithdatarest.interfaces.IUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
@Table(name = "customer")
@Access(AccessType.FIELD)
public class Customer implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = " at least 1 character is required")
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

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

    @NotNull(message = "is required")
    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "customer")
    private User user;

    public Customer() {
        super();
    }

    public Customer(String firstName, String lastName, List<Review> reviewList, Employee employee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reviewList = reviewList;
        this.employee = employee;
    }

    public Customer(String firstName, String lastName, List<Review> reviewList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reviewList = reviewList;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "[ " + this.id + "/n" +
                this.firstName + "/n" +
                this.lastName +"/n" +
                this.email + " ]";
    }
}