package org.projetperso.crypto.service;

import lombok.RequiredArgsConstructor;
import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
import org.projetperso.crypto.repo.AlertRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertScheduler {

    private final AlertRepository alertRepository;
    private final CoinService coinService;
    private final AlertEmailSender alertEmailSender;

    @Scheduled(fixedDelayString = "${crypto.alerts.scheduler-delay}")
    public void checkAlerts() {
        final var alerts = alertRepository.findByActive(true);
        System.out.println(alerts.size());
        for (final var alert : alerts) {
            final var price = coinService.getCoinByID(alert.getCoinId()).getCurrentPrice();
            if (shouldTrigger(alert, price)) {
                alert.setPrice(price);
                alertEmailSender.send(alert);
                alert.setLastTriggered(LocalDateTime.now());
                alertRepository.save(alert);
            }
        }
    }

    private boolean shouldTrigger(Alert alert, BigDecimal price) {
        if (alert.getType() == AlertType.RECURRING) {
            return alert.getLastTriggered() == null ||
                    Duration.between(alert.getLastTriggered(), LocalDateTime.now()).getSeconds() >= alert.getIntervalSeconds();
        }
        if (alert.getType() == AlertType.THRESHOLD) {
            return price.compareTo(alert.getTargetPrice()) >= 0;
        }
        return false;
    }
}
