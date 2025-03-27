package org.projetperso.crypto.mapper;

import org.projetperso.crypto.dto.Coin;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;

public class CoinMapper {

    public CoinMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Coin fromRaw(CoinGeckoRawDTO raw) {
        if (raw == null || raw.getMarketData() == null) return null;

        return new Coin(
                raw.getId(),
                raw.getSymbol(),
                raw.getName(),
                raw.getMarketData().getCurrentPrice().get("usd"),
                raw.getMarketData().getMarketCap().get("usd"),
                raw.getMarketData().getPriceChangePercentage24h(),
                raw.getMarketData().getHigh24h().get("usd"),
                raw.getMarketData().getLow24h().get("usd"),
                raw.getMarketData().getTotalVolume().get("usd"),
                raw.getMarketCapRank()
        );
    }
}
