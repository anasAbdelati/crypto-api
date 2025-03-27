package org.projetperso.crypto.mapper;

import org.junit.jupiter.api.Test;
import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinMapperTest {

    @Test
    void shouldMapRawDtoToCoinCorrectly() {

        CoinGeckoRawDTO raw = CoinGeckoRawDTO.builder()
                .id("bitcoin")
                .symbol("btc")
                .name("Bitcoin")
                .marketCapRank(1)
                .marketData(CoinGeckoRawDTO.MarketData.builder()
                        .currentPrice(Map.of("usd", new BigDecimal("69000")))
                        .marketCap(Map.of("usd", new BigDecimal("1000000000")))
                        .high24h(Map.of("usd", new BigDecimal("69500")))
                        .low24h(Map.of("usd", new BigDecimal("68000")))
                        .totalVolume(Map.of("usd", new BigDecimal("30000000")))
                        .priceChangePercentage24h(new BigDecimal("1.25"))
                        .build())
                .build();

        CoinDTO coin = CoinMapper.fromRaw(raw);

        assertEquals("bitcoin", coin.getId());
        assertEquals(new BigDecimal("69000"), coin.getCurrentPrice());
    }
}
