package com.SourceOne.models;

import com.SourceOne.enums.ChangeType;
import jakarta.persistence.*;

@Entity
@Table(name = "so_change_log")
public class ChangeLog extends CommonDataModel {

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType;

    @Column(name = "change_summary", length = 1000)
    private String changeSummary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public User getChangesBy() {
        return changedBy;
    }

    public void setChangedBy(User changesBy) {
        this.changedBy = changesBy;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
    }
}
