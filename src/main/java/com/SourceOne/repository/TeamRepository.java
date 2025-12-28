package com.SourceOne.repository;

import com.SourceOne.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {

    boolean existsByName(String name);

    Optional<Team> findByName(String name);

}
