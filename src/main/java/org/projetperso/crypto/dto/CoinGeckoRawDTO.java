package org.projetperso.crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CoinGeckoRawDTO {

    private String id;
    private String symbol;
    private String name;

    @JsonProperty("market_cap_rank")
    private int marketCapRank;

    @JsonProperty("market_data")
    private MarketData marketData;

    @Data
    public static class MarketData {
        @JsonProperty("current_price")
        private Map<String, BigDecimal> currentPrice;

        @JsonProperty("market_cap")
        private Map<String, BigDecimal> marketCap;

        @JsonProperty("total_volume")
        private Map<String, BigDecimal> totalVolume;

        @JsonProperty("price_change_percentage_24h")
        private BigDecimal priceChangePercentage24h;

        @JsonProperty("high_24h")
        private Map<String, BigDecimal> high24h;

        @JsonProperty("low_24h")
        private Map<String, BigDecimal> low24h;
    }
}
