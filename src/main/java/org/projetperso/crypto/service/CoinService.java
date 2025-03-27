package org.projetperso.crypto.service;

import org.projetperso.crypto.API.CoinGeckoAPI;
import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinService {

    private final CoinGeckoAPI coinGeckoAPI;

    public CoinService(CoinGeckoAPI coinGeckoAPI) {
        this.coinGeckoAPI = coinGeckoAPI;
    }

    public CoinDTO getCoinByID(String id) {
        return  coinGeckoAPI.getCoinById(id);
    }

    public List<CoinPreview> getCoinList() {
        return coinGeckoAPI.getTopCoins();
    }
}
