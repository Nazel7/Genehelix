package com.genehelix.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "status_mr")
@Access(AccessType.FIELD)
public class MedicalResultStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "create_time_stamp")
    private Date uploadTimeStamp;

    @UpdateTimestamp
    @Column(name = "update_time_stamp")
    private Date udateTimeStamp;

    @OneToOne
    @JoinColumn(name = "hc_service")
    private HcService hcService;


    public MedicalResultStatus(){

        super();
    }
    public MedicalResultStatus(int id, String status, HcService hcService, Employee employee) {
        this.id = id;
        this.status = status;
        this.hcService = hcService;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUploadTimeStamp() {
        return uploadTimeStamp;
    }

    public void setUploadTimeStamp(Date uploadTimeStamp) {
        this.uploadTimeStamp = uploadTimeStamp;
    }

    public Date getUdateTimeStamp() {
        return udateTimeStamp;
    }

    public void setUdateTimeStamp(Date udateTimeStamp) {
        this.udateTimeStamp = udateTimeStamp;
    }

    public HcService getHcService() {
        return hcService;
    }

    public void setHcService(HcService hcService) {
        this.hcService = hcService;
    }
}
