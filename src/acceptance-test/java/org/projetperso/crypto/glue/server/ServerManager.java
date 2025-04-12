package org.projetperso.crypto.glue.server;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.repo.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerManager {

    private final CoinGeckoManager coinGeckoManager;
    private final KeycloakManager keycloakManager;
    private final AlertRepository alertRepository;

    public ServerManager(CoinGeckoManager coinGeckoManager, KeycloakManager keycloakManager, AlertRepository alertRepository) {
        this.coinGeckoManager = coinGeckoManager;
        this.keycloakManager = keycloakManager;
        this.alertRepository = alertRepository;
    }

    public void serveCoin( CoinGeckoRawDTO dto) {
        coinGeckoManager.serveCoin(dto);
    }

    public void serveCoinNotFound(String coinId) {
        coinGeckoManager.serveCoinNotFound(coinId);
    }

    public void serveTopCoins(List<CoinPreview> coins) {
        coinGeckoManager.serveTopCoins(coins);
    }

    public void mockKeycloakUserCreation() {
        keycloakManager.resetExpectations();
        keycloakManager.mockUserCreation();
        keycloakManager.mockGetUserRole();
        keycloakManager.mockUserRoleAssign();
        keycloakManager.mockTokenRequest();
    }

    public void mockUserCreationFailure() {
        keycloakManager.resetExpectations();
        keycloakManager.mockUserCreationFailure();
        keycloakManager.mockTokenRequest();
    }

    public void startKeycloak() {
        keycloakManager.start();
    }

    public void startCoinGeckoApi() {
        coinGeckoManager.start();
    }

    public void serveUserAlerts(List<Alert> alerts) {
        alertRepository.saveAll(alerts);
    }
}
