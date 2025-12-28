package com.SourceOne.repository;

import com.SourceOne.enums.EntityType;
import com.SourceOne.models.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentationRepository extends JpaRepository<Documentation, String> {

    List<Documentation> findByEntityTypeAndEntityId(EntityType entityType, String entityId);

}
