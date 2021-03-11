package com.genehelix.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_Photo")
@Access(AccessType.FIELD)
public class EmployeeProfilePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 @Column(name = "photo_name")
    private String photoName;

    @Lob
    @Column(name = "c_photo", nullable = false)
    private String profilePhoto;

    @Column(name = "photo_size")
    private long  photoSize;

    @CreationTimestamp
    @Column(name = "upload_timestamp")
    private Date uploadTimeStamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Date updateTimeStamp;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    public EmployeeProfilePhoto(){
        super();
    }

    public EmployeeProfilePhoto(int id, String profilePhoto, Employee employee) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.employee= employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public long getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(long photoSize) {
        this.photoSize = photoSize;
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
}
