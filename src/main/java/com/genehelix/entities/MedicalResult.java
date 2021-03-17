package com.genehelix.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medical_result")
@Access(AccessType.FIELD)
public class MedicalResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "m_name", unique = true)
    private String name;

    @Lob
    @Column(name = "m_result", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] medicalResult;

    @Column(name = "r_size")
    private long  size;

    @CreationTimestamp
    @Column(name = "upload_timestamp")
    private Date uploadTimeStamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Date updateTimeStamp;

    @ManyToOne
    @JoinColumn(name = "r_employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "r_customer_id")
    private Customer customer;


    public MedicalResult(){

        super();
    }

    public MedicalResult(int id, String name, byte[] medicalResult, long size, Date uploadTimeStamp,
                         Date updateTimeStamp, Employee employee, Customer customer) {
        this.id = id;
        this.name = name;
        this.medicalResult = medicalResult;
        this.size = size;
        this.uploadTimeStamp = uploadTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
        this.employee = employee;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMedicalResult() {
        return medicalResult;
    }

    public void setMedicalResult(byte[] medicalResult) {
        this.medicalResult = medicalResult;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTimeStamp() {
        return uploadTimeStamp;
    }

    public void setUploadTimeStamp(Date uploadTimeStamp) {
        this.uploadTimeStamp = uploadTimeStamp;
    }

    public Date getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(Date updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }
}
