package com.SourceOne.repository;

import com.SourceOne.models.Api;
import com.SourceOne.models.SystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiRepository extends JpaRepository<Api, String> {

    List<Api> findBySystem(SystemEntity system);

    Optional<Api> findByName(String name);
}
