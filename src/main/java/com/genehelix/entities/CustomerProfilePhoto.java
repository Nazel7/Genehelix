package com.genehelix.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_Photo")
@Access(AccessType.FIELD)
public class CustomerProfilePhoto {
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
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public CustomerProfilePhoto(){
        super();
    }

    public CustomerProfilePhoto(int id, String profilePhoto, Customer customer) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.customer = customer;
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



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
