package com.SourceOne.service;

import com.SourceOne.models.SystemEntity;
import com.SourceOne.models.Team;
import com.SourceOne.models.User;
import com.SourceOne.repository.SystemEntityRepository;
import com.SourceOne.repository.TeamRepository;
import com.SourceOne.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SystemService extends AbstractCDMService<SystemEntity> {

    private final SystemEntityRepository systemEntityRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public SystemService(SystemEntityRepository systemEntityRepository, TeamRepository teamRepository, UserRepository userRepository, UserService userService) {
        this.systemEntityRepository = systemEntityRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public SystemEntity createSystem(SystemEntity system) {

        if (system.getOwnerTeam() != null && system.getOwnerTeam().getId() != null) {
            Team ownerTeam = teamRepository.findById(system.getOwnerTeam().getId()).orElseThrow(() -> new RuntimeException("Owner team not found"));
            system.setOwnerTeam(ownerTeam);
        }

        if (system.getCreatedBy() != null) {
            User createdBy = userRepository.findById(system.getCreatedBy().getId()).orElseThrow(() -> new RuntimeException("User not found"));
            system.setCreatedBy(createdBy);
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
