package com.sourceone.repository;

import com.sourceone.enums.EntityType;
import com.sourceone.models.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentationRepository extends JpaRepository<Documentation, String> {

    List<Documentation> findByEntityTypeAndEntityId(EntityType entityType, String entityId);

}
