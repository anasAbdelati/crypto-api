package org.projetperso.crypto.API;

import org.projetperso.crypto.dto.Coin;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;
import org.projetperso.crypto.mapper.CoinMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import java.net.URI;

@Service
public class CoinGeckoAPI {

    private final RestClient restClient;
    private static final String BASE_URL = "/api/v3/coins/";

    public CoinGeckoAPI(@Value("${coinGecko.url}") final String url) {
        this.restClient = RestClient.create(url);
    }

    public Coin getCoinById(String id) {
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

    private static URI uriCoinByID(final UriBuilder builder, String id){
        builder.path(BASE_URL).path(id);
        return builder.build();
    }
}
