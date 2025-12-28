package com.SourceOne.models;

import com.SourceOne.security.PasswordUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "so_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends CommonDataModel {

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Teams the user belongs to
    @ManyToMany(mappedBy = "members")
    @JsonIgnore // prevents infinite JSON recursion
    private Set<Team> teams = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void hashPassword() {
        if (password != null && !PasswordUtil.isHashed(password)) {
            password = PasswordUtil.hash(password);
        }
    }

    // getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
