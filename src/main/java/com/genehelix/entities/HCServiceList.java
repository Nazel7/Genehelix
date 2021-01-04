package com.genehelix.entities;

import javax.persistence.*;

@Entity
@Table(name = "hc_serviceList")
@Access(AccessType.FIELD)
public class HCServiceList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "service_title")
    private String serviceTitle;

    public HCServiceList(){
        super();
    }

    public HCServiceList(int id, String serviceTitle) {
        Id = id;
        this.serviceTitle = serviceTitle;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }
}
