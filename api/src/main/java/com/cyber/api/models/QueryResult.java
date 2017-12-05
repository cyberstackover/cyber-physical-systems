package com.cyber.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
    @TypeDef(name = "JsonMapTypeHash", typeClass = com.bazarnazar.pgjson.JsonMapType.class)})
@Table(name = "query_result")
public class QueryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "query_id")
    private SavedQuery query;

  //  @JsonIgnore
    @Type(type = "JsonMapTypeHash")
    private List<Map<String, Object>> result;

    private Long starttime;

    private Long endtime;

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

    public SavedQuery getQuery() {
        return query;
    }

    public void setQuery(SavedQuery query) {
        this.query = query;
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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
