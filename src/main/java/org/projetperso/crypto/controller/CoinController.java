package org.projetperso.crypto.controller;

import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.service.AlertService;
import org.projetperso.crypto.service.CoinService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/1/coin")
public class CoinController {

    private final CoinService coinService;
    private final AlertService alertService;

    public CoinController(CoinService coinService, AlertService alertService) {
        this.coinService = coinService;
        this.alertService = alertService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public CoinDTO getCoin(@PathVariable String id) {
        return coinService.getCoinByID(id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('user')")
    public List<CoinPreview> getCoinlist() {
        return coinService.getCoinList();
    }

    @PostMapping("/subscribe/recurring/{id}")
    @PreAuthorize("hasRole('user')")
    public void subscribeToCoin(@PathVariable String id,@RequestParam int time) {
         alertService.subscribeCoin(id,time);
    }

    @PostMapping("/subscribe/threshold/{id}")
    @PreAuthorize("hasRole('user')")
    public void subscribeThreshold(@PathVariable String id, @RequestParam BigDecimal targetPrice) {
        alertService.subscribeThreshold(id, targetPrice);
    }
}
