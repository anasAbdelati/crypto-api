package org.projetperso.crypto.service;

import org.projetperso.crypto.API.CoinGeckoAPI;
import org.projetperso.crypto.dto.Coin;
import org.springframework.stereotype.Service;

@Service
public class CoinService {

    private final CoinGeckoAPI coinGeckoAPI;

    public CoinService(CoinGeckoAPI coinGeckoAPI) {
        this.coinGeckoAPI = coinGeckoAPI;
    }

    public Coin getCoinByID(String id) {
        return  coinGeckoAPI.getCoinById(id);
    }
}
