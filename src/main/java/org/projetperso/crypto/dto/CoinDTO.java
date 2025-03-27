package org.projetperso.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CoinDTO {

    private String id;
    private String symbol;
    private String name;
    private BigDecimal currentPrice;
    private BigDecimal marketCap;
    private BigDecimal priceChangePercentage24h;
    private BigDecimal high24h;
    private BigDecimal low24h;
    private BigDecimal totalVolume;
    private int marketCapRank;
    private String description;
}
