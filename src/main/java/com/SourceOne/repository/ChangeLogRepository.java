package com.SourceOne.repository;

import com.SourceOne.models.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, String> {

}
