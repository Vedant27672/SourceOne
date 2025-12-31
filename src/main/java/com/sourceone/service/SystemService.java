package com.sourceone.service;

import com.sourceone.models.SystemEntity;
import com.sourceone.models.Team;
import com.sourceone.models.User;
import com.sourceone.repository.SystemEntityRepository;
import com.sourceone.repository.TeamRepository;
import com.sourceone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SystemService extends AbstractCDMService<SystemEntity> {

    private final SystemEntityRepository systemEntityRepository;
    private final TeamRepository teamRepository;
    private final UserService userService;

    public SystemService(SystemEntityRepository systemEntityRepository, TeamRepository teamRepository, UserService userService) {
        this.systemEntityRepository = systemEntityRepository;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Transactional
    public SystemEntity createSystem(SystemEntity system) {

        if (system.getOwnerTeam() != null && system.getOwnerTeam().getId() != null) {
            Team ownerTeam = teamRepository.findById(system.getOwnerTeam().getId()).orElseThrow(() -> new RuntimeException("Owner team not found"));
            system.setOwnerTeam(ownerTeam);
        }

        if (system.getCreatedBy() != null) {
            User user = userService.findByUsername(system.getCreatedBy());
            system.setCreatedBy(user.getUsername());
        }

        return systemEntityRepository.save(system);
    }

    @Transactional
    public SystemEntity updateSystem(SystemEntity system) {

        SystemEntity existingSystem = systemEntityRepository.findById(system.getId()).orElseThrow(() -> new RuntimeException("System not found"));

        existingSystem.setName(system.getName());
        existingSystem.setType(system.getType());
        existingSystem.setDescription(system.getDescription());
        existingSystem.setSourceOfTruth(system.isSourceOfTruth());

        if (system.getOwnerTeam() != null && system.getOwnerTeam().getId() != null) {
            Team ownerTeam = teamRepository.findById(system.getOwnerTeam().getId()).orElseThrow(() -> new RuntimeException("Team not found"));
            existingSystem.setOwnerTeam(ownerTeam);
        }

        if (system.getUpdatedBy() != null) {
            User user = userService.findByUsername(system.getUpdatedBy());
            existingSystem.setUpdatedBy(user.getUsername());
        }

        return systemEntityRepository.save(existingSystem);
    }

    public SystemEntity getSystemById(String id) {
        return systemEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("System not found with ID: " + id));
    }
}
