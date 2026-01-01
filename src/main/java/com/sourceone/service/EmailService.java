package com.sourceone.service;

public interface EmailService {

    void sendVerificationOtp(String toEmail, String otp);
}
