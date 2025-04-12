package org.projetperso.crypto.service;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
import org.projetperso.crypto.exceptions.DataAlreadyExistsException;
import org.projetperso.crypto.exceptions.DataNotFoundException;
import org.projetperso.crypto.repo.AlertRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void subscribeCoin(String coinId,int time) {

        final var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var userId = jwt.getSubject();
        final var email = jwt.getClaimAsString("email");

        final var alert=alertRepository.findByUserIdAndCoinIdAndType(userId,coinId,AlertType.RECURRING);
        if(!alert.isEmpty()) {
            throw new DataAlreadyExistsException("Alert already exists");
        }
        final var alertCoin= Alert.builder().userId(userId).coinId(coinId).active(true).type(AlertType.RECURRING).intervalSeconds(time).email(email).build();
        alertRepository.save(alertCoin);
    }

    public void subscribeThreshold(String coinId, BigDecimal targetPrice) {

        final var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var userId = jwt.getSubject();
        final var email = jwt.getClaimAsString("email");

        final var alertCoin= Alert.builder().userId(userId).coinId(coinId).active(true).type(AlertType.THRESHOLD).targetPrice(targetPrice).email(email).build();
        alertRepository.save(alertCoin);
    }

    public List<Alert> findAllAlerts() {
        final var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var userId = jwt.getSubject();
        return alertRepository.findByUserId(userId);
    }

    public Alert findAlertById(Long id) {
        final var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var userId = jwt.getSubject();
        return alertRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new DataNotFoundException("Alert not found"));
    }

    public void deleteAlert(Long alertId) {
        final var alert=findAlertById(alertId);
        alertRepository.delete(alert);
    }
}
