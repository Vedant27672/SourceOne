package com.sourceone.repository;

import com.sourceone.models.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, String> {

}
