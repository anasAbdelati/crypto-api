package org.projetperso.crypto.controller;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.service.AlertService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1/alert")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('user')")
    public List<Alert> getAlerts() {
        return alertService.findAllAlerts();
    }

    @GetMapping("/{alertId}")
    @PreAuthorize("hasRole('user')")
    public Alert getAlert(@PathVariable Long alertId) {
        return alertService.findAlertById(alertId);
    }

    @DeleteMapping("/{alertId}")
    @PreAuthorize("hasRole('user')")
    public void deleteAlert(@PathVariable Long alertId) {
        alertService.deleteAlert(alertId);
    }
}
