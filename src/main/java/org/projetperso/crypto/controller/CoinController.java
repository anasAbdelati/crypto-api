package org.projetperso.crypto.controller;

import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.service.CoinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1/coin")
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/{id}")
    public CoinDTO getCoin(@PathVariable String id) {
        return coinService.getCoinByID(id);
    }

    @GetMapping("/list")
    public List<CoinPreview> getCoinlist() {
        return coinService.getCoinList();
    }
}
