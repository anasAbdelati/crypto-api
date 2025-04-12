package org.projetperso.crypto.glue.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.projetperso.crypto.dto.CoinDTO;
import org.projetperso.crypto.dto.CoinGeckoRawDTO;
import org.projetperso.crypto.dto.CoinPreview;
import org.projetperso.crypto.glue.server.ServerManager;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.projetperso.crypto.glue.utils.Utils.retrieve;

public class CoinSteps {

    private final HttpClient httpClient;
    private final ServerManager serverManager;

    public CoinSteps(HttpClient httpClient, ServerManager serverManager) {
        this.httpClient = httpClient;
        this.serverManager = serverManager;
    }

    @Given("the following coin exists:")
    public void serveCoin(final CoinGeckoRawDTO coin){
        serverManager.serveCoin(coin);
    }

    @Given("{string} doesn't exist")
    public void coinDoesnTExist(final String coinId) {
        serverManager.serveCoinNotFound(coinId);
    }

    @Given("the following coins exists:")
    public void theFollowingCoinsExists(List<CoinPreview> coins) {
        serverManager.serveTopCoins(coins);
    }

    @When("the user gets the coin {string}")
    public void theUserGetsTheCoinBitcoin(final String coinId) {
        httpClient.httpGet("/api/1/coin/"+coinId);
    }

    @When("the user gets the coin list")
    public void theUserGetsTheCoinList() {
        httpClient.httpGet("/api/1/coin/list");
    }

    @When("the user subscribes to recurring alerts for coin {string} with time {int}")
    public void theUserSubscribesToRecurringAlertsForCoinEthereumWithTime(final String coinId, final int arg0) {
        httpClient.httpPost("/api/1/coin/subscribe/recurring/"+coinId+"?time="+arg0);
    }

    @When("the user subscribes to threshold alerts for coin {string} with target price {int}")
    public void theUserSubscribesToThresholdAlertsForCoinEthereumWithTargetPrice(final String coinId, final int arg0) {
        httpClient.httpPost("/api/1/coin/subscribe/threshold/"+coinId+"?targetPrice="+arg0);
    }

    @Then("the following coin is returned")
    public void theFollowingCoinIsReturned(final CoinDTO coin) throws UnsupportedEncodingException, JsonProcessingException {
        final var actuals = retrieve(httpClient.getCurrentResponse(),CoinDTO.class);
        assertThat(actuals).usingRecursiveComparison().isEqualTo(coin);
    }

    @Then("the following coins are returned")
    public void theFollowingCoinsAreReturned(final List<CoinPreview> coins) throws UnsupportedEncodingException, JsonProcessingException {
        final var actuals = retrieve(httpClient.getCurrentResponse(),CoinPreview.class,CoinPreview[].class);
        assertThat(actuals).usingRecursiveComparison().isEqualTo(coins);
    }
}
