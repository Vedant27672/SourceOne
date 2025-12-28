package com.SourceOne.models;

import com.SourceOne.enums.ActiveStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class CommonDataModel {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    protected String id;

    @Version
    protected Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status", nullable = false)
    protected ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "created_by")
    protected User createdBy;

    @Column(name = "updated_by")
    protected String updatedBy;

    @Column(name = "creation_time", updatable = false)
    protected LocalDateTime creationTime;

    @Column(name = "last_modified_time")
    protected LocalDateTime lastModifiedTime;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.creationTime = LocalDateTime.now();
        this.lastModifiedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedTime = LocalDateTime.now();
    }

    // ---------- Getters & Setters ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Long getVersion() {
        return version;
    }

    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        CommonDataModel that = (CommonDataModel) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
