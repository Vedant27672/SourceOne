package com.SourceOne.service;

import com.SourceOne.enums.ChangeType;
import com.SourceOne.models.ChangeLog;
import com.SourceOne.models.User;
import com.SourceOne.repository.ChangeLogRepository;
import com.SourceOne.repository.UserRepository;
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
    public ChangeLog createChangeLog(String entityType, ChangeType changeType, User changedBy, String changeSummary) {

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
    public ChangeLog updateChangeLogSummary(String changeLogId, String newSummary, User updatedBy) {

        ChangeLog changeLog = changeLogRepository.findById(changeLogId).orElseThrow(() -> new RuntimeException("ChangeLog not found"));

        if (updatedBy != null) {
            User persistedUser = userRepository.findById(updatedBy.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            changeLog.setUpdatedBy(persistedUser);
        }

        changeLog.setChangeSummary(newSummary);
        return changeLogRepository.save(changeLog);
    }
}

