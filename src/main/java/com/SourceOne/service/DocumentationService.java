package com.SourceOne.service;

import com.SourceOne.enums.EntityType;
import com.SourceOne.models.Documentation;
import com.SourceOne.models.User;
import com.SourceOne.repository.DocumentationRepository;
import com.SourceOne.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentationService extends AbstractCDMService<Documentation> {

    private final DocumentationRepository documentationRepository;
    private final UserRepository userRepository;

    public DocumentationService(DocumentationRepository documentationRepository, UserRepository userRepository) {
        this.documentationRepository = documentationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Documentation createDocumentation(Documentation documentation, String createdById) {

        if (documentation.getEntityType() == null || documentation.getEntityId() == null || documentation.getContent() == null) {
            throw new RuntimeException("Required fields missing");
        }

        User createdBy = userRepository.findById(createdById).orElseThrow(() -> new RuntimeException("User not found with ID: " + createdById));

        documentation.setCreatedBy(createdBy);
        return documentationRepository.save(documentation);
    }

    @Transactional
    public Documentation updateDocumentation(Documentation documentation) {

        Documentation existingDoc = documentationRepository.findById(documentation.getId()).orElseThrow(() -> new RuntimeException("Documentation not found with ID: " + documentation.getId()));

        existingDoc.setEntityType(documentation.getEntityType());
        existingDoc.setEntityId(documentation.getEntityId());
        existingDoc.setContent(documentation.getContent());
        existingDoc.setDocVersion(documentation.getDocVersion());
        existingDoc.setStale(documentation.isStale());

        if (documentation.getUpdatedBy() != null) {
            User updatedBy = userRepository.findById(documentation.getUpdatedBy().getId()).orElseThrow(() -> new RuntimeException("User not found"));
            existingDoc.setUpdatedBy(updatedBy);
        }

        return documentationRepository.save(existingDoc);
    }

    public List<Documentation> fetchDocumentationByEntityTypeAndEntityId(EntityType entityType, String entityId) {
        return documentationRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }
}
