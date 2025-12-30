package com.SourceOne.models;

import com.SourceOne.enums.ChangeType;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "so_change_log")
public class ChangeLog extends CommonDataModel {

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType;

    @Type(JsonType.class)
    @Column(name = "change_summary", columnDefinition = "json")
    private JsonNode changeSummary;


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

    public JsonNode getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(JsonNode changeSummary) {
        this.changeSummary = changeSummary;
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
