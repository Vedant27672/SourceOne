package com.sourceone.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Utility runner for password encryption and verification.
 *
 * Usage:
 *  encrypt <plainPassword>
 *  verify <plainPassword> <encryptedPassword>
 */
public class PasswordUtilityRunner {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtilityRunner.class);
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        // ================= ENCRYPT PASSWORD ===================
        logger.info("================= ENCRYPT PASSWORD ===================");
        String plainPassword1 = "User3password";
        String encryptedPassword1 = encrypt(plainPassword1);
        logger.info("Password encrypted successfully.");
        logger.info("Encrypted password: {}", encryptedPassword1);

        // ================= VERIFY PASSWORD ====================
        logger.info("================= VERIFY PASSWORD ====================");
        String plainPassword2 = "User3password";
        String encryptedPassword2 = "$2a$10$qTqhFaEOlro.2ZniFgQX/uvWCbIJWLkQTvEFLnrz5hQTHaT8nxpSC";
        boolean matches = verify(plainPassword2, encryptedPassword2);
        logger.info("Password verification executed.");
        logger.info(matches ? "TRUE" : "FALSE");
    }

    private static String encrypt(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    private static boolean verify(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}
