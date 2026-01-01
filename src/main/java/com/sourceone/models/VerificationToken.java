package com.sourceone.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token", indexes = {@Index(name = "idx_verification_token_email", columnList = "email")})
public class VerificationToken extends CommonDataModel {

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String otpHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* ================= Lifecycle ================= */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ================= Getters & Setters ================= */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpHash() {
        return otpHash;
    }

    public void setOtpHash(String otpHash) {
        this.otpHash = otpHash;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
