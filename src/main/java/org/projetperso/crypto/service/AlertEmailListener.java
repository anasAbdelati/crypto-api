package org.projetperso.crypto.service;

import lombok.RequiredArgsConstructor;
import org.projetperso.crypto.dto.Alert;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertEmailListener {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "user-alerts", groupId = "email-service")
    public void onMessage(Alert msg) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(msg.getEmail());
        email.setSubject("Crypto Alert Triggered");
        email.setText(msg.getCoinId()+" Reached "+msg.getTargetPrice()+" $");
        mailSender.send(email);
    }
}

