package com.cyber.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String username;

    @JsonIgnore
    @NotEmpty
    private String password;

    @NotNull
    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = true)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true)
    private Date updated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Profile profile;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_account", joinColumns = {
        @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "account_id")})
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private List<Authority> authorities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<SavedQuery> queries;

    public User() {
    }

    public User(String username, String password, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(User user) {

        super();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String[] getAuthorities() {
        String[] result = new String[authorities.size()];
        for (int i = 0; i < authorities.size(); i++) {
            result[i] = authorities.get(i).getAuthority();
        }
        return result;
    }

    public void setAuthorities(List<Authority> authority) {
        this.authorities = authority;
    }

    @PrePersist
    protected void onCreate() {
        this.updated = this.created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled + ", created=" + created + ", updated=" + updated + ", profile=" + profile + ", accounts=" + accounts + ", authorities=" + authorities + ", queries=" + queries + '}';
    }

}
