package org.projetperso.crypto.glue.data;

import io.cucumber.java.DataTableType;
import org.projetperso.crypto.dto.*;

import java.math.BigDecimal;
import java.util.Map;

public class DataTableTypeMapper {

    @DataTableType
    public UserRegistrationRequest mapUserRegistration(Map<String, String> entry) {
        return UserRegistrationRequest.builder()
                .username(entry.get("username"))
                .password(entry.get("password"))
                .firstName(entry.get("first"))
                .lastName(entry.get("last"))
                .email(entry.get("email"))
                .build();
    }

    @DataTableType
    public CoinGeckoRawDTO mapToCoinGecko(Map<String, String> entry) {
        return CoinGeckoRawDTO.builder()
                .id(entry.get("id"))
                .name(entry.get("name"))
                .symbol(entry.get("symbol"))
                .marketCapRank(1)
                .description(Map.of("en", "Some description"))
                .marketData(CoinGeckoRawDTO.MarketData.builder()
                        .currentPrice(Map.of("usd", BigDecimal.valueOf(42000.00)))
                        .marketCap(Map.of("usd", BigDecimal.valueOf(800_000_000_000L)))
                        .totalVolume(Map.of("usd", BigDecimal.valueOf(50_000_000_000L)))
                        .priceChangePercentage24h(BigDecimal.valueOf(2.5))
                        .high24h(Map.of("usd", BigDecimal.valueOf(43000.00)))
                        .low24h(Map.of("usd", BigDecimal.valueOf(41000.00)))
                        .build())
                .build();
    }


    @DataTableType
    public CoinDTO mapToCoinDTO(Map<String, String> entry) {
        return CoinDTO.builder()
                .id(entry.get("id"))
                .name(entry.get("name"))
                .symbol(entry.get("symbol"))
                .description("Some description")
                .currentPrice(BigDecimal.valueOf(42000.00))
                .marketCap(BigDecimal.valueOf(800_000_000_000L))
                .priceChangePercentage24h(BigDecimal.valueOf(2.5))
                .high24h(BigDecimal.valueOf(43000.00))
                .low24h(BigDecimal.valueOf(41000.00))
                .totalVolume(BigDecimal.valueOf(50_000_000_000L))
                .marketCapRank(1)
                .build();
    }

    @DataTableType
    public CoinPreview mapToCoinPreview(Map<String, String> entry) {
        return CoinPreview.builder()
                .id(entry.get("id"))
                .name(entry.get("name"))
                .symbol(entry.get("symbol"))
                .currentPrice(new BigDecimal(50))
                .marketCap(new BigDecimal(50))
                .build();
    }

    @DataTableType
    public Alert mapToAlert(Map<String, String> entry) {
        return Alert.builder()
                .id(entry.containsKey("id") ? Long.parseLong(entry.get("id")) : null)
                .userId(entry.get("userId"))
                .coinId(entry.get("coinId"))
                .type(AlertType.valueOf(entry.get("type")))
                .active(Boolean.parseBoolean(entry.get("active")))
                .build();
    }
}
