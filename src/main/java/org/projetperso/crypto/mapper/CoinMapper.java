package org.projetperso.crypto.mapper;

import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;

public class CoinMapper {

    public static final String CURRENCY = "usd";

    public CoinMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CoinDTO fromRaw(CoinGeckoRawDTO raw) {
        if (raw == null || raw.getMarketData() == null) return null;

        return CoinDTO.builder()
                .id(raw.getId())
                .symbol(raw.getSymbol())
                .name(raw.getName())
                .description(raw.getDescription() != null ? raw.getDescription().get("en") : null)
                .currentPrice(raw.getMarketData().getCurrentPrice().get(CURRENCY))
                .marketCap(raw.getMarketData().getMarketCap().get(CURRENCY))
                .priceChangePercentage24h(raw.getMarketData().getPriceChangePercentage24h())
                .high24h(raw.getMarketData().getHigh24h().get(CURRENCY))
                .low24h(raw.getMarketData().getLow24h().get(CURRENCY))
                .totalVolume(raw.getMarketData().getTotalVolume().get(CURRENCY))
                .marketCapRank(raw.getMarketCapRank())
                .build();
    }
}
