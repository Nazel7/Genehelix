package com.spring.boot.App2.springbootprojectwithdatarest.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "review")
@Table(name = "review")
@Access(AccessType.FIELD)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 3, message = "at least one value is required")
    @Column(name = "review_message")
    private String reviewMessage;

    @ManyToOne
    Customer customer = null;

    public Review() {
        super();
    }

    public Review(String reviewMessage, Customer customer) {
        this.reviewMessage = reviewMessage;
        this.customer = customer;
    }

    public Review(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String review_message) {
        this.reviewMessage = review_message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "[ " + this.id + "/n" +
                this.reviewMessage + "/n" + "]";
    }
}
