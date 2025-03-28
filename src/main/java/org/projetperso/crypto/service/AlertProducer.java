package org.projetperso.crypto.service;

import lombok.RequiredArgsConstructor;
import org.projetperso.crypto.dto.Alert;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertProducer {

    private final KafkaTemplate<String, Alert> kafkaTemplate;

    public void send(Alert message) {
        kafkaTemplate.send("user-alerts", message.getUserId(), message);
    }
}

