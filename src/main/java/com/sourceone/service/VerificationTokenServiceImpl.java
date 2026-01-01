package com.sourceone.service;

import com.sourceone.models.VerificationToken;
import com.sourceone.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;

    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ================= OTP GENERATION ================= */

    @Override
    public String generateAndSaveOtp(String email) {

        verificationTokenRepository.deleteByEmail(email);

        String otp = generateOtp();
        String otpHash = passwordEncoder.encode(otp);

        VerificationToken token = new VerificationToken();
        token.setEmail(email);
        token.setOtpHash(otpHash);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));

        verificationTokenRepository.save(token);

        logger.info("Verification OTP generated for email={}", email);

        return otp; // ⚠ returned ONLY for email sending
    }


    /* ================= OTP VALIDATION ================= */

    @Override
    public boolean validateOtp(String email, String otp) {

        VerificationToken token = verificationTokenRepository.findByEmail(email).orElse(null);

        if (token == null) {
            logger.warn("OTP validation failed: no token found for email={}", email);
            return false;
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            logger.warn("OTP expired for email={}", email);
            verificationTokenRepository.deleteByEmail(email);
            return false;
        }

        boolean matches = passwordEncoder.matches(otp, token.getOtpHash());

        if (!matches) {
            logger.warn("Invalid OTP provided for email={}", email);
            return false;
        }

        // OTP is valid → delete token
        verificationTokenRepository.deleteByEmail(email);
        logger.info("OTP validated successfully for email={}", email);

        return true;
    }

    /* ================= INTERNAL ================= */

    private String generateOtp() {
        SecureRandom random = new SecureRandom();

        int min = 1;
        for (int i = 1; i < OTP_LENGTH; i++) {
            min *= 10;
        }

        int range = min * 9;

        int otp = min + random.nextInt(range);
        return String.valueOf(otp);
    }

}
