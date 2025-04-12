package org.projetperso.crypto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.dto.AlertType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class AlertSchedulerTest {

    private AlertScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new AlertScheduler(null, null, null);
    }

    @Test
    void returnsTrue_ifRecurringAndNeverTriggered() {
        var alert = Alert.builder()
                .type(AlertType.RECURRING)
                .intervalSeconds(60)
                .lastTriggered(null)
                .build();

        boolean result = callShouldTrigger(alert, BigDecimal.TEN);
        assertThat(result).isTrue();
    }

    @Test
    void returnsFalse_ifRecurringAndIntervalNotPassed() {
        var alert = Alert.builder()
                .type(AlertType.RECURRING)
                .intervalSeconds(300)
                .lastTriggered(LocalDateTime.now().minusSeconds(100))
                .build();

        boolean result = callShouldTrigger(alert, BigDecimal.TEN);
        assertThat(result).isFalse();
    }

    @Test
    void returnsTrue_ifRecurringAndIntervalPassed() {
        var alert = Alert.builder()
                .type(AlertType.RECURRING)
                .intervalSeconds(300)
                .lastTriggered(LocalDateTime.now().minusSeconds(600))
                .build();

        boolean result = callShouldTrigger(alert, BigDecimal.TEN);
        assertThat(result).isTrue();
    }

    @Test
    void returnsFalse_ifThresholdNotMet() {
        var alert = Alert.builder()
                .type(AlertType.THRESHOLD)
                .targetPrice(new BigDecimal("50000"))
                .build();

        boolean result = callShouldTrigger(alert, new BigDecimal("45000"));
        assertThat(result).isFalse();
    }

    @Test
    void returnsTrue_ifThresholdMetOrExceeded() {
        var alert = Alert.builder()
                .type(AlertType.THRESHOLD)
                .targetPrice(new BigDecimal("50000"))
                .build();

        boolean result1 = callShouldTrigger(alert, new BigDecimal("50000"));
        boolean result2 = callShouldTrigger(alert, new BigDecimal("51000"));

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    //cuz method is private
    private boolean callShouldTrigger(Alert alert, BigDecimal price) {
        try {
            var method = AlertScheduler.class
                    .getDeclaredMethod("shouldTrigger", Alert.class, BigDecimal.class);
            method.setAccessible(true);
            return (boolean) method.invoke(scheduler, alert, price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
