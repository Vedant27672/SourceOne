package com.sourceone.models;

import jakarta.persistence.*;

@Entity
@Table(name = "so_system")
public class SystemEntity extends CommonDataModel {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_team_id", nullable = false)
    private Team ownerTeam;

    @Column(name = "source_of_truth", nullable = false)
    private boolean sourceOfTruth = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Team getOwnerTeam() {
        return ownerTeam;
    }

    public void setOwnerTeam(Team ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public boolean isSourceOfTruth() {
        return sourceOfTruth;
    }

    public void setSourceOfTruth(boolean sourceOfTruth) {
        this.sourceOfTruth = sourceOfTruth;
    }

    public enum Type {
        BACKEND,
        MOBILE,
        DATABASE,
        EXTERNAL,
        UI
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
