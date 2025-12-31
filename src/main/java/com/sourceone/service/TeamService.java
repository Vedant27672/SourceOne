package com.sourceone.service;

import com.sourceone.models.Team;
import com.sourceone.models.User;
import com.sourceone.repository.TeamRepository;
import com.sourceone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TeamService extends AbstractCDMService<Team> {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Team createTeam(Team team) {

        if (team.getName() == null || team.getName().isBlank()) {
            throw new RuntimeException("Team name is required");
        }

        if (teamRepository.existsByName(team.getName())) {
            throw new RuntimeException("Team name already exists: " + team.getName());
        }

        if (team.getOwner() != null) {
            User owner = userRepository.findById(team.getOwner().getId()).orElseThrow(() -> new RuntimeException("Owner not found"));
            team.setOwner(owner);
        }

        if (team.getMembers() != null && !team.getMembers().isEmpty()) {
            team.setMembers(team.getMembers().stream().map(member -> userRepository.findById(member.getId()).orElseThrow(() -> new RuntimeException("User not found"))).collect(java.util.stream.Collectors.toSet()));
        }

        return teamRepository.save(team);
    }

    @Transactional
    public Team updateTeam(Team team) {

        Team existingTeam = teamRepository.findById(team.getId()).orElseThrow(() -> new RuntimeException("Team not found"));

        if (!existingTeam.getName().equals(team.getName()) && teamRepository.existsByName(team.getName())) {
            throw new RuntimeException("Team name already exists");
        }

        existingTeam.setName(team.getName());
        existingTeam.setDescription(team.getDescription());

        if (team.getUpdatedBy() != null) {
            User user = userService.findByUsername(team.getUpdatedBy());
            existingTeam.setUpdatedBy(user.getUsername());
        }

        if (team.getOwner() != null) {
            User owner = userRepository.findById(team.getOwner().getId()).orElseThrow(() -> new RuntimeException("Owner not found"));
            existingTeam.setOwner(owner);
        }

        if (team.getMembers() != null) {
            existingTeam.getMembers().clear();
            team.getMembers().forEach(member -> {
                User user = userRepository.findById(member.getId()).orElseThrow(() -> new RuntimeException("User not found"));
                existingTeam.getMembers().add(user);
            });
        }

        return teamRepository.save(existingTeam);
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));
    }

    @Transactional
    public Team addMembers(Team team, Set<User> users, User updatedBy) {

        if (team == null || team.getId() == null) {
            throw new RuntimeException("Team is required");
        }

        Team existingTeam = teamRepository.findById(team.getId()).orElseThrow(() -> new RuntimeException("Team not found with ID: " + team.getId()));

        if (users != null && !users.isEmpty()) {
            for (User member : users) {
                if (member == null || member.getId() == null) {
                    throw new RuntimeException("Invalid user in members list");
                }

                User persistedUser = userRepository.findById(member.getId()).orElseThrow(() -> new RuntimeException("User not found with ID: " + member.getId()));

                existingTeam.getMembers().add(persistedUser); // Set prevents duplicates
            }
        }

        if (updatedBy != null && updatedBy.getUsername() != null) {
            existingTeam.setUpdatedBy(updatedBy.getUsername());
        }

        return teamRepository.save(existingTeam);
    }


}
