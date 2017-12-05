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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "saved_queries")
public class SavedQuery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String sql;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_running")
    private boolean isRunning;

    private String database;

    @Column(name = "is_cached")
    private boolean isCached;

    @Column(name = "interval_query")
    private String intervalQuery;

    @Column(name = "is_replaced")
    private boolean isReplaced;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "query", cascade = CascadeType.ALL)
    private List<QueryResult> queryResult;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isIsReplaced() {
        return isReplaced;
    }

    public void setIsReplaced(boolean isReplaced) {
        this.isReplaced = isReplaced;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isIsCached() {
        return isCached;
    }

    public void setIsCached(boolean isCached) {
        this.isCached = isCached;
    }

    public String getIntervalQuery() {
        return intervalQuery;
    }

    public void setIntervalQuery(String intervalQuery) {
        this.intervalQuery = intervalQuery;
    }

    public List<QueryResult> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<QueryResult> queryResult) {
        this.queryResult = queryResult;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
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
