package org.projetperso.crypto.service;

import lombok.RequiredArgsConstructor;
import org.projetperso.crypto.dto.Alert;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertEmailSender {

    private final JavaMailSender mailSender;

    public void send(Alert msg) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(msg.getEmail());
        email.setSubject("Crypto Alert Triggered");
        email.setText(msg.getCoinId()+" Reached "+msg.getTargetPrice()+" $");
        mailSender.send(email);
    }
}

