package com.sourceone.repository;

import com.sourceone.models.SystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemEntityRepository extends JpaRepository<SystemEntity, String> {

    Optional<SystemEntity> findByName(String name);
}

