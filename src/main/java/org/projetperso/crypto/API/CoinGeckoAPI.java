package org.projetperso.crypto.API;

import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.exceptions.CoinGeckoApiException;
import org.projetperso.crypto.mapper.CoinMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import java.net.URI;
import java.util.List;

@Service
public class CoinGeckoAPI {

    private final RestClient restClient;
    private static final String BASE_URL = "/api/v3/coins/";

    public CoinGeckoAPI(@Value("${coinGecko.url}") final String url) {
        this.restClient = RestClient.create(url);
    }

    public CoinDTO getCoinById(String id) {
        try{
            CoinGeckoRawDTO raw = restClient.get()
                    .uri(builder -> uriCoinByID(builder, id))
                    .retrieve()
                    .body(CoinGeckoRawDTO.class);
            return CoinMapper.fromRaw(raw);
        }
        catch (HttpClientErrorException e){
            throw new CoinGeckoApiException(e.getMessage());
        }
    }

    public List<CoinPreview> getTopCoins() {
        try {
            return restClient.get()
                    .uri(CoinGeckoAPI::uriTop50Coins)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

        } catch (HttpClientErrorException e) {
            throw new CoinGeckoApiException(e.getMessage());
        }
    }


    private static URI uriCoinByID(final UriBuilder builder, String id){
        builder.path(BASE_URL).path(id);
        return builder.build();
    }

    private static URI uriTop50Coins(final UriBuilder builder) {
        return builder
                .path("/api/v3/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", 50)
                .queryParam("page", 1)
                .build();
    }
}
