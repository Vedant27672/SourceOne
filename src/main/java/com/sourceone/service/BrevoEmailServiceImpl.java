package com.sourceone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrevoEmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(BrevoEmailServiceImpl.class);

    private static final String BREVO_SMTP_API = "https://api.brevo.com/v3/smtp/email";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    @Override
    public void sendVerificationOtp(String toEmail, String otp) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            Map<String, Object> sender = new HashMap<>();
            sender.put("email", senderEmail);
            sender.put("name", senderName);

            Map<String, Object> recipient = new HashMap<>();
            recipient.put("email", toEmail);

            Map<String, Object> body = new HashMap<>();
            body.put("sender", sender);
            body.put("to", List.of(recipient));
            body.put("subject", "SourceOne - Email Verification OTP");
            body.put("htmlContent", buildHtmlContent(otp));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(BREVO_SMTP_API, request, String.class);

            logger.info("OTP email sent successfully via Brevo to email={}", toEmail);

        } catch (Exception ex) {
            logger.error("Failed to send OTP email via Brevo to email={}", toEmail, ex);
            throw ex;
        }
    }

    private String buildHtmlContent(String otp) {
        return """
                <html>
                    <body>
                        <p>Hello,</p>
                        <p>Your <b>SourceOne verification OTP</b> is:</p>
                        <h2>%s</h2>
                        <p>This OTP is valid for <b>10 minutes</b>.</p>
                        <p>Please do not share this OTP with anyone.</p>
                        <br/>
                        <p>Regards,<br/>SourceOne Team</p>
                    </body>
                </html>
                """.formatted(otp);
    }
}
