package org.projetperso.crypto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;
import org.projetperso.crypto.exceptions.DataAlreadyExistsException;
import org.projetperso.crypto.exceptions.DataNotFoundException;
import org.projetperso.crypto.repo.AlertRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    private final String userId = "user-123";
    private final String email = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockSecurityContext(userId, email);
    }

    private void mockSecurityContext(String userId, String email) {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn(userId);
        when(jwt.getClaimAsString("email")).thenReturn(email);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(jwt);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }

    @Test
    void subscribeCoin_createsNewRecurringAlert() {
        when(alertRepository.findByUserIdAndCoinIdAndType(userId, "bitcoin", AlertType.RECURRING))
                .thenReturn(List.of());
        alertService.subscribeCoin("bitcoin", 300);
        verify(alertRepository, times(1)).save(any(Alert.class));
    }

    @Test
    void subscribeCoin_throwsIfAlreadyExists() {
        when(alertRepository.findByUserIdAndCoinIdAndType(userId, "bitcoin", AlertType.RECURRING))
                .thenReturn(List.of(mock(Alert.class)));
        assertThatThrownBy(() -> alertService.subscribeCoin("bitcoin", 300))
                .isInstanceOf(DataAlreadyExistsException.class)
                .hasMessageContaining("Alert already exists");
    }

    @Test
    void subscribeThreshold_savesThresholdAlert() {
        alertService.subscribeThreshold("ethereum", new BigDecimal("1800.50"));
        verify(alertRepository).save(argThat(alert ->
                alert.getCoinId().equals("ethereum") &&
                        alert.getTargetPrice().equals(new BigDecimal("1800.50")) &&
                        alert.getType() == AlertType.THRESHOLD
        ));
    }

    @Test
    void findAllAlerts_returnsAlertsForUser() {
        var alerts = List.of(mock(Alert.class));
        when(alertRepository.findByUserId(userId)).thenReturn(alerts);
        var result = alertService.findAllAlerts();
        assertThat(result).isEqualTo(alerts);
    }

    @Test
    void findAlertById_returnsAlertIfExists() {
        var alert = mock(Alert.class);
        when(alertRepository.findByIdAndUserId(1L, userId)).thenReturn(Optional.of(alert));
        var result = alertService.findAlertById(1L);
        assertThat(result).isEqualTo(alert);
    }

    @Test
    void findAlertById_throwsIfNotFound() {
        when(alertRepository.findByIdAndUserId(1L, userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> alertService.findAlertById(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Alert not found");
    }

    @Test
    void deleteAlert_deletesFoundAlert() {
        var alert = mock(Alert.class);
        when(alertRepository.findByIdAndUserId(1L, userId)).thenReturn(Optional.of(alert));
        alertService.deleteAlert(1L);
        verify(alertRepository).delete(alert);
    }
}
