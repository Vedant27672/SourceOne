package com.sourceone.service;

import com.sourceone.enums.ChangeType;
import com.sourceone.models.ChangeLog;
import com.sourceone.models.User;
import com.sourceone.repository.ChangeLogRepository;
import com.sourceone.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ChangeLogService extends AbstractCDMService<ChangeLog> {

    private final ChangeLogRepository changeLogRepository;
    private final UserRepository userRepository;

    public ChangeLogService(ChangeLogRepository changeLogRepository, UserRepository userRepository) {
        this.changeLogRepository = changeLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ChangeLog createChangeLog(String entityType, ChangeType changeType, User changedBy, JsonNode changeSummary) {

        if (changedBy == null || changedBy.getId() == null) {
            throw new RuntimeException("changedBy is required");
        }

        User persistedUser = userRepository.findById(changedBy.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        ChangeLog changeLog = new ChangeLog();
        changeLog.setEntityType(entityType);
        changeLog.setChangeType(changeType);
        changeLog.setChangedBy(persistedUser);
        changeLog.setChangeSummary(changeSummary);
        return changeLogRepository.save(changeLog);
    }

    @Transactional
    public ChangeLog updateChangeLogSummary(String changeLogId, JsonNode newSummary, User updatedBy) {

        ChangeLog changeLog = changeLogRepository.findById(changeLogId).orElseThrow(() -> new RuntimeException("ChangeLog not found"));

        if (updatedBy != null) {
            User persistedUser = userRepository.findById(updatedBy.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            changeLog.setUpdatedBy(persistedUser.getUsername());
        }

        changeLog.setChangeSummary(newSummary);
        return changeLogRepository.save(changeLog);
    }
}

