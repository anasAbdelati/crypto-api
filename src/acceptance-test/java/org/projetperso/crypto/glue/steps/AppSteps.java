package org.projetperso.crypto.glue.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.projetperso.crypto.glue.server.ServerManager;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class AppSteps {

    private final HttpClient httpClient;
    private final ServerManager serverManager;

    public AppSteps(HttpClient httpClient, ServerManager serverManager) {
        this.httpClient = httpClient;
        this.serverManager = serverManager;
    }

    @Given("keycloak is running")
    public void keycloakIsRunning() {
        serverManager.startKeycloak();
    }

    @Given("coinGeckoApi is running")
    public void coingeckoapiIsRunning() {
        serverManager.startCoinGeckoApi();
    }

    @Given("a user is authenticated with the role {string}")
    public void aUserIsAuthenticatedWithTheRoleUser(final String role) {
        httpClient.setRoles(role);
    }

    @Then("a {string} status code is returned")
    public void aOKStatusCodeIsReturned(final String status) {
        final var got=httpClient.getCurrentResponse().getStatus();
        assertThat(HttpStatus.valueOf(got)).isEqualTo(HttpStatus.valueOf(status));
    }
}
