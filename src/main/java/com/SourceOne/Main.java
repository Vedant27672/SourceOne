package com.SourceOne;

import com.SourceOne.enums.APIStatus;
import com.SourceOne.enums.ActiveStatus;
import com.SourceOne.enums.ChangeType;
import com.SourceOne.enums.EntityType;
import com.SourceOne.enums.HttpMethod;
import com.SourceOne.models.*;
import com.SourceOne.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runUpdates(UserRepository userRepository, TeamRepository teamRepository, SystemEntityRepository systemEntityRepository, ApiRepository apiRepository, DocumentationRepository documentationRepository, ChangeLogRepository changeLogRepository) {
        return args -> {
            updateDummyData(userRepository, teamRepository, systemEntityRepository, apiRepository, documentationRepository, changeLogRepository);
        };
    }

    // ================= INSERT (NOT CALLED) =================
    private void insertDummyData(UserRepository userRepository, TeamRepository teamRepository, SystemEntityRepository systemEntityRepository, ApiRepository apiRepository, DocumentationRepository documentationRepository, ChangeLogRepository changeLogRepository) {
        // your commented insert code stays here if needed later
    }

    // ================= UPDATE (CALLED) =================
    private void updateDummyData(UserRepository userRepository, TeamRepository teamRepository, SystemEntityRepository systemEntityRepository, ApiRepository apiRepository, DocumentationRepository documentationRepository, ChangeLogRepository changeLogRepository) {

        // ---- Update User ----
        userRepository.findByUsername("bob").ifPresent(user -> {
            user.setName("Bob Marley");
            user.setRole("ADMIN");
            userRepository.save(user);
        });

        // ---- Update Team ----
        teamRepository.findByName("Platform Team").ifPresent(team -> {
            userRepository.findByUsername("bob").ifPresent(team::setOwner);
            team.setDescription("Handles core platform & infrastructure");
            teamRepository.save(team);
        });

        // ---- Update System ----
        systemEntityRepository.findByName("Order System").ifPresent(system -> {
            system.setDescription("Core order processing system (updated)");
            system.setSourceOfTruth(false);
            systemEntityRepository.save(system);
        });

        // ---- Update API ----
        apiRepository.findByName("Create Order").ifPresent(api -> {
            api.setEndpoint("/orders/create");
            api.setStatus(APIStatus.DEPRECATED);
            apiRepository.save(api);
        });

        // ---- Update Documentation ----
        systemEntityRepository.findByName("Order System").ifPresent(system -> {
            documentationRepository.findByEntityTypeAndEntityId(EntityType.SYSTEM, system.getId()).stream().findFirst().ifPresent(doc -> {
                doc.setContent("Updated documentation for Order System");
                doc.setStale(true);
                documentationRepository.save(doc);
            });
        });

        // ---- Update Change Log ----
        changeLogRepository.findAll().stream().findFirst().ifPresent(log -> {
            log.setChangeSummary("Order system creation reviewed and updated");
            changeLogRepository.save(log);
        });

        System.out.println("🔄 Dummy data updated successfully");
    }
}
