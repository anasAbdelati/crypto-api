package org.projetperso.crypto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.service.AlertService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1/alert")
@Tag(name = "Alert", description = "Endpoints related to user alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @Operation(summary = "Get all alerts for the current user")
    @ApiResponse(
            responseCode = "200",
            description = "List of alerts",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alert.class))
    )
    @GetMapping("/list")
    @PreAuthorize("hasRole('user')")
    public List<Alert> getAlerts() {
        return alertService.findAllAlerts();
    }

    @Operation(summary = "Get a specific alert by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alert found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alert.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alert not found",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Alert not found"))
            )
    })
    @GetMapping("/{alertId}")
    @PreAuthorize("hasRole('user')")
    public Alert getAlert(
            @Parameter(description = "Alert ID", example = "123") @PathVariable Long alertId
    ) {
        return alertService.findAlertById(alertId);
    }

    @Operation(summary = "Delete an alert by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alert deleted successfully",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Alert deleted successfully"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alert not found",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Alert not found"))
            )
    })
    @DeleteMapping("/{alertId}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> deleteAlert(
            @Parameter(description = "Alert ID", example = "123") @PathVariable Long alertId
    ) {
        alertService.deleteAlert(alertId);
        return ResponseEntity.ok("Alert deleted successfully");
    }
}
