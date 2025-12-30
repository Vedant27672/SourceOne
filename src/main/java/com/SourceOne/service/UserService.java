package com.SourceOne.service;

import com.SourceOne.models.Team;
import com.SourceOne.models.User;
import com.SourceOne.repository.TeamRepository;
import com.SourceOne.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService extends AbstractCDMService<User> {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public UserService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    /* ---------------- CREATE ---------------- */

    public User create(User user, User createdBy) {
        if (createdBy != null && createdBy.getId() != null) {
            User userdb = findByUsername(createdBy.getUsername());
            user.setCreatedBy(userdb.getUsername());
        }
        return userRepository.save(user);
    }

    /* ---------------- READ ---------------- */

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /* ---------------- UPDATE ---------------- */

    public User update(User user, User updatedBy) {
        User existing = findByUsername(user.getUsername());
        existing.setName(user.getName());
        existing.setRole(user.getRole());
        if (updatedBy != null && updatedBy.getId() != null) {
            User persistedUpdatedBy = userRepository.findById(updatedBy.getId()).orElseThrow(() -> new RuntimeException("UpdatedBy user not found"));
            existing.setUpdatedBy(persistedUpdatedBy.getUsername());
        }
        return userRepository.save(existing);
    }


    /* ---------------- TEAM MANAGEMENT ---------------- */

    public User addTeams(String username, Set<Team> teams, User updatedBy) {
        User existingUser = findByUsername(username);
        if (teams != null) {
            for (Team team : teams) {
                if (team == null || team.getId() == null) {
                    throw new RuntimeException("Invalid team");
                }
                Team persistedTeam = teamRepository.findById(team.getId()).orElseThrow(() -> new RuntimeException("Team not found with ID: " + team.getId()));
                existingUser.getTeams().add(persistedTeam);
            }
        }
        setUpdatedBy(existingUser, updatedBy);
        return userRepository.save(existingUser);
    }

    public User removeTeams(String username, Set<Team> teams, User updatedBy) {
        User existingUser = findByUsername(username);
        if (teams != null) {
            for (Team team : teams) {
                if (team == null || team.getId() == null) {
                    throw new RuntimeException("Invalid team");
                }
                Team persistedTeam = teamRepository.findById(team.getId()).orElseThrow(() -> new RuntimeException("Team not found with ID: " + team.getId()));
                existingUser.getTeams().remove(persistedTeam);
            }
        }
        setUpdatedBy(existingUser, updatedBy);
        return userRepository.save(existingUser);
    }

    /* ---------------- DELETE ---------------- */

    public User deleteAndReturn(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
        return user;
    }


    /* ---------------- HELPER ---------------- */

    private void setUpdatedBy(User target, User updatedBy) {
        if (updatedBy != null && updatedBy.getId() != null) {
            User persistedUpdatedBy = userRepository.findById(updatedBy.getId()).orElseThrow(() -> new RuntimeException("UpdatedBy user not found"));
            target.setUpdatedBy(persistedUpdatedBy.getUsername());
        }
    }
}
