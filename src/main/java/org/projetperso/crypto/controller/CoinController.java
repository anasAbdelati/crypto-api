package org.projetperso.crypto.controller;

import org.projetperso.crypto.dto.Coin;
import org.projetperso.crypto.service.CoinService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1/coin")
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/{id}")
    public Coin getCoin(@PathVariable String id) {
        return coinService.getCoinByID(id) ;
    }
}
