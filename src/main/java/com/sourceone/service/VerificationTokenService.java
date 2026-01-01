package com.sourceone.service;

public interface VerificationTokenService {

    String generateAndSaveOtp(String email);

    boolean validateOtp(String email, String otp);
}

