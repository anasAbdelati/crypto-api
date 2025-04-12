package org.projetperso.crypto.service;

import lombok.RequiredArgsConstructor;
import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertEmailSender {

    private final JavaMailSender mailSender;

    public void send(Alert msg) {
        final var email = new SimpleMailMessage();
        email.setTo(msg.getEmail());
        email.setSubject("Crypto Alert Triggered");
        final var alertPrice=msg.getType()==AlertType.RECURRING ? msg.getPrice() : msg.getTargetPrice();
        email.setText(msg.getCoinId()+" Reached "+alertPrice+" $");
        mailSender.send(email);
    }
}

