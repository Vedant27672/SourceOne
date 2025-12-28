package com.SourceOne.repository;

import com.SourceOne.models.SystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemEntityRepository extends JpaRepository<SystemEntity, String> {

    Optional<SystemEntity> findByName(String name);
}

