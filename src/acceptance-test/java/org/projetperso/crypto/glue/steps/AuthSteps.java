package org.projetperso.crypto.glue.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.projetperso.crypto.dto.UserRegistrationRequest;
import org.projetperso.crypto.glue.server.ServerManager;

public class AuthSteps {

    private final HttpClient httpClient;
    private final ServerManager serverManager;
    private final ObjectMapper objectMapper;
    public AuthSteps(HttpClient httpClient, ServerManager serverManager, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.serverManager = serverManager;
        this.objectMapper = objectMapper;
    }

    @Given("Keycloak allows user registration")
    public void keycloakAllowsUserRegistration() {
        serverManager.mockKeycloakUserCreation();
    }

    @Given("Keycloak rejects user registration")
    public void keycloakRejectsUserRegistration() {
        serverManager.mockUserCreationFailure();
    }

    @When("I register a user with:")
    public void registerUserWith(UserRegistrationRequest user) throws Exception {
        String json = objectMapper.writeValueAsString(user);
        httpClient.httpPostJson("/api/public/register", json);
    }
}
