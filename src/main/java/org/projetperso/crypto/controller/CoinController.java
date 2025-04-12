package org.projetperso.crypto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.service.AlertService;
import org.projetperso.crypto.service.CoinService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/1/coin")
@Tag(name = "Coin", description = "Endpoints related to coin data and subscriptions")
public class CoinController {

    private final CoinService coinService;
    private final AlertService alertService;

    public CoinController(CoinService coinService, AlertService alertService) {
        this.coinService = coinService;
        this.alertService = alertService;
    }

    @Operation(summary = "Get coin details by ID", description = "Retrieves full coin details for a given coin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coin found", content = @Content(schema = @Schema(implementation = CoinDTO.class))),
            @ApiResponse(responseCode = "404", description = "Coin not found", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public CoinDTO getCoin(
            @Parameter(description = "Coin ID", example = "bitcoin") @PathVariable String id
    ) {
        return coinService.getCoinByID(id);
    }

    @Operation(summary = "List top coins", description = "Retrieves a list of top trending or most relevant coins.")
    @ApiResponse(
            responseCode = "200",
            description = "List of coins",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoinPreview.class))
    )
    @GetMapping("/list")
    @PreAuthorize("hasRole('user')")
    public List<CoinPreview> getCoinlist() {
        return coinService.getCoinList();
    }

    @Operation(
            summary = "Subscribe to recurring coin alert",
            description = "Subscribes the current user to receive alerts at a recurring interval (in seconds)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Subscription successful",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Subscription created successfully"))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Subscription already exists",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Alert already exists"))
            )
    })
    @PostMapping("/subscribe/recurring/{id}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> subscribeToCoin(
            @Parameter(description = "Coin ID", example = "bitcoin") @PathVariable String id,
            @Parameter(description = "Interval time in seconds", example = "3600") @RequestParam int time
    ) {
        alertService.subscribeCoin(id, time);
        return ResponseEntity.ok("Subscription created successfully");
    }

    @Operation(
            summary = "Subscribe to price threshold alert",
            description = "Subscribes the current user to be notified when the coin price crosses a target value."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Subscription successful",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Subscription created successfully"))
    )
    @PostMapping("/subscribe/threshold/{id}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> subscribeThreshold(
            @Parameter(description = "Coin ID", example = "bitcoin") @PathVariable String id,
            @Parameter(description = "Target price to trigger alert", example = "50000.00") @RequestParam BigDecimal targetPrice
    ) {
        alertService.subscribeThreshold(id, targetPrice);
        return ResponseEntity.ok("Subscription created successfully");
    }
}
