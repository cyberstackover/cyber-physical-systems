package com.cyber.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "organization_name")
    private String organizationName;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private Country country;

    private String state;

    private String city;

    @ManyToMany(mappedBy = "accounts")
    private List<User> users;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = true)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true)
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @PrePersist
    protected void onCreate() {
        this.updated = this.created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }
}
