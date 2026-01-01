package com.sourceone.controller;

import com.sourceone.models.User;
import com.sourceone.service.EmailService;
import com.sourceone.service.UserService;
import com.sourceone.service.VerificationTokenService;
import com.sourceone.util.JWTUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/verify")
public class VerificationController {

    private static final Logger logger = LoggerFactory.getLogger(VerificationController.class);

    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private static String MESSAGE = "message";

    public VerificationController(VerificationTokenService verificationTokenService, EmailService emailService, UserService userService, JWTUtil jwtUtil) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /* ================= SEND OTP ================= */

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> body) {

        String email = body.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        User user = userService.findByUsername(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("User already verified");
        }

        String otp = verificationTokenService.generateAndSaveOtp(email);
        emailService.sendVerificationOtp(email, otp);

        logger.info("Verification OTP sent for email={}", email);

        return ResponseEntity.ok("Verification OTP sent successfully");
    }

    /* ================= VERIFY OTP ================= */

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String otp = body.get("otp");

        if (email == null || otp == null || email.isBlank() || otp.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE, "Email and OTP are required"));
        }

        User user = userService.findByUsername(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE, "User not found"));
        }

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE, "User already verified"));
        }

        boolean valid = verificationTokenService.validateOtp(email, otp);

        if (!valid) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE, "Invalid or expired OTP"));
        }

        user.setVerified(true);
        userService.save(user);

        String token = jwtUtil.generateToken(user.getUsername());

        logger.info("User verified successfully and JWT issued for email={}", email);

        return ResponseEntity.ok(Map.of(MESSAGE, "User verified successfully", "token", token));
    }
}
