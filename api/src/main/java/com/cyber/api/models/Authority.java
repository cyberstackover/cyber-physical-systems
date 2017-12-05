package com.cyber.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cyber.api.models.Authority.AuthorityId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
public class Authority implements Serializable {

    @JsonIgnore
    @Id
    private String username;

    @Id
    private String authority;

    @JsonIgnore
    @ManyToOne
    private User user;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class AuthorityId implements Serializable {

        private String username;

        private String authority;

        public AuthorityId() {}

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof AuthorityId) {
                AuthorityId id = (AuthorityId) obj;

                if (!id.getUsername().equals(username)) {
                    return false;
                }

                if (!id.getAuthority().equals(authority)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return username.hashCode() + authority.hashCode();
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

    }
}
