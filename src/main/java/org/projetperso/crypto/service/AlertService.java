package org.projetperso.crypto.service;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
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

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        final var alert=alertRepository.findByUserIdAndCoinIdAndType(userId,coinId,AlertType.RECURRING);
        if(!alert.isEmpty()) {
            throw new DataAlreadyExists("Alert already exists");
        }
        final var alertCoin= Alert.builder().userId(userId).coinId(coinId).active(true).type(AlertType.RECURRING).intervalSeconds(time).email(email).build();
        alertRepository.save(alertCoin);
    }

    public void subscribeThreshold(String coinId, BigDecimal targetPrice) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        final var alertCoin= Alert.builder().userId(userId).coinId(coinId).active(true).type(AlertType.THRESHOLD).targetPrice(targetPrice).email(email).build();
        alertRepository.save(alertCoin);
    }

    public List<Alert> findAllAlerts() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();
        return alertRepository.findByUserId(userId);
    }

    public Alert findAlertById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getSubject();
        return alertRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new DataNotFound("Alert not found"));
    }

    public void deleteAlert(Long alertId) {
        final var alert=findAlertById(alertId);
        alertRepository.delete(alert);
    }
}
