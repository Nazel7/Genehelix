package com.genehelix.entities;

import javax.persistence.*;

@Entity
@Table(name= "hc_service")
@Access(AccessType.FIELD)
public class HcService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int Id;

    @Column(name= "name")
    private String name;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    private CustomerDetails customerDetails;

    public HcService(){
        super();

    }

    public HcService(int id, String name, CustomerDetails customerDetails) {
        Id = id;
        this.name = name;
        this.customerDetails = customerDetails;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    @Override
    public String toString() {
        return "HcService{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", customerDetails=" + customerDetails +
                '}';
    }
}
