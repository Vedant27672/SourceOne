package com.SourceOne.models;

import com.SourceOne.enums.EntityType;
import jakarta.persistence.*;

@Entity
@Table(name = "so_documentation")
public class Documentation extends CommonDataModel {

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "doc_version")
    private String docVersion;

    @Column(name = "is_stale", nullable = false)
    private boolean stale = false;

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    public boolean isStale() {
        return stale;
    }

    public void setStale(boolean stale) {
        this.stale = stale;
    }
}
