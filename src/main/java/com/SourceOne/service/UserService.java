package com.SourceOne.service;

import com.SourceOne.models.Team;
import com.SourceOne.models.User;
import com.SourceOne.repository.TeamRepository;
import com.SourceOne.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService extends AbstractCDMService<User> {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;


    public UserService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public User createUser(User user) {

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new RuntimeException("Username is required");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {

        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));

        existingUser.setName(user.getName());

        if (!existingUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        existingUser.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(user.getPassword());
        }

        existingUser.setRole(user.getRole());

        if (user.getUpdatedBy() != null && user.getUpdatedBy().getId() != null) {
            User updatedBy = userRepository.findById(user.getUpdatedBy().getId()).orElseThrow(() -> new RuntimeException("UpdatedBy user not found"));
            existingUser.setUpdatedBy(updatedBy);
        }

        return userRepository.save(existingUser);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Transactional
    public User addTeams(User user, Set<Team> teams, User updatedBy) {

        if (user == null || user.getId() == null) {
            throw new RuntimeException("User is required");
        }

        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));

        if (teams != null && !teams.isEmpty()) {
            for (Team team : teams) {
                if (team == null || team.getId() == null) {
                    throw new RuntimeException("Invalid team in teams list");
                }

                Team persistedTeam = teamRepository.findById(team.getId()).orElseThrow(() -> new RuntimeException("Team not found with ID: " + team.getId()));

                existingUser.getTeams().add(persistedTeam); // Set prevents duplicates
            }
        }

        if (updatedBy != null && updatedBy.getId() != null) {
            User persistedUpdatedBy = userRepository.findById(updatedBy.getId()).orElseThrow(() -> new RuntimeException("UpdatedBy user not found"));
            existingUser.setUpdatedBy(persistedUpdatedBy);
        }

        return userRepository.save(existingUser);
    }

}
