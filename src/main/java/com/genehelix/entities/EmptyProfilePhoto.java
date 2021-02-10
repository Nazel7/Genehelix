package com.genehelix.entities;

import javax.persistence.*;

@Entity
@Table(name = "empty_photo")
public class EmptyProfilePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Lob
    @Column(name = "c_photo", columnDefinition = "MEDIUMBLOB",nullable = false)
    private String profilePhoto;

    public EmptyProfilePhoto(){
        super();
    }

    public EmptyProfilePhoto(int id, String profilePhoto) {
        this.id = id;
        this.profilePhoto = profilePhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
