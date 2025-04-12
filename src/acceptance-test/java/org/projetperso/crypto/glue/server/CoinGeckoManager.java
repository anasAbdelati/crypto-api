package org.projetperso.crypto.glue.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoinGeckoManager extends ApiMockServer {

    private static final String BASE_URL = "/api/v3/coins/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public CoinGeckoManager() {
        super(10525);
    }

    public void serveCoin(CoinGeckoRawDTO coin) {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath(BASE_URL + coin.getId())
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(asJson(coin))
        );
    }

    public void serveCoinNotFound(String coinId) {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath(BASE_URL + coinId)
        ).respond(
                HttpResponse.response()
                        .withStatusCode(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"coin not found\"}")
        );
    }

    public void serveTopCoins(List<CoinPreview> coins) {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/api/v3/coins/markets")
                        .withQueryStringParameter("vs_currency", "usd")
                        .withQueryStringParameter("order", "market_cap_desc")
                        .withQueryStringParameter("per_page", "50")
                        .withQueryStringParameter("page", "1")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(asJson(coins))
        );
    }

    private String asJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
