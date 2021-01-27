package com.genehelix.entities;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;

@Entity
@Table(name="user_resume")
@Access(AccessType.FIELD)
public class UserResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "resume", nullable = false)
    private byte[] resume;

    @Column(name = "resume_name", nullable = false)
    private String resumeName;

    @Column(name = "resume_size", nullable = false)
    private long resumeSize;

    @Column(name="date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "customer_resume", unique = true)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "employee_resume", unique = true)
    private Employee employee;

    public UserResume(){
        super();
    }

    public UserResume(int id, byte[] resume, Customer customer, Employee employee) {
        Id = id;
        this.resume = resume;
        this.customer = customer;
        this.employee = employee;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public long getResumeSize() {
        return resumeSize;
    }

    public void setResumeSize(long resumeSize) {
        this.resumeSize = resumeSize;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
