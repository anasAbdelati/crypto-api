package org.projetperso.crypto.glue.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.projetperso.crypto.dto.Alert;
import org.projetperso.crypto.glue.server.ServerManager;
import java.io.UnsupportedEncodingException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.projetperso.crypto.glue.utils.Utils.retrieve;

public class AlertSteps {

    private final HttpClient httpClient;
    private final ServerManager serverManager;

    public AlertSteps(HttpClient httpClient, ServerManager serverManager) {
        this.httpClient = httpClient;
        this.serverManager = serverManager;
    }


    @Given("the following alerts exist for the user:")
    public void theFollowingAlertsExistForTheUser(List<Alert> alerts) {
        serverManager.serveUserAlerts(alerts);
    }

    @When("the user gets the alert list")
    public void theUserGetsTheAlertList() {
        httpClient.httpGet("/api/1/alert/list");
    }

    @When("the user gets the alert with id {long}")
    public void theUserGetsTheAlertWithId(Long id) {
        httpClient.httpGet("/api/1/alert/" + id);
    }

    @When("the user deletes the alert with id {long}")
    public void theUserDeletesTheAlertWithId(Long id) {
        httpClient.httpDelete("/api/1/alert/" + id);
    }

    @Then("the following alert is returned:")
    public void theFollowingAlertsAreReturned(final Alert alert) throws UnsupportedEncodingException, JsonProcessingException {
        final var actuals = retrieve(httpClient.getCurrentResponse(),Alert.class);
        assertThat(actuals).usingRecursiveComparison().isEqualTo(alert);
    }

    @Then("the following alerts are returned:")
    public void theFollowingAlertIsReturned(final List<Alert> alert) throws UnsupportedEncodingException, JsonProcessingException {
        final var actuals = retrieve(httpClient.getCurrentResponse(),Alert.class,Alert[].class);
        assertThat(actuals).usingRecursiveComparison().isEqualTo(alert);
    }
}
