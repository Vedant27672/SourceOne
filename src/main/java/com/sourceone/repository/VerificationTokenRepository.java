package com.sourceone.repository;

import com.sourceone.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByEmail(String email);

    void deleteByEmail(String email);
}
