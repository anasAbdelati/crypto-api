package org.projetperso.crypto.service;

import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.projetperso.crypto.dto.UserRegistrationRequest;
import org.projetperso.crypto.exceptions.UserNotCreatedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakAdminService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    public void createUser(UserRegistrationRequest request) {
        final var keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm("master")
                .username(adminUsername)
                .password(adminPassword)
                .clientId(clientId)
                .build();

        final var credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(request.getPassword());

        final var user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        final var realmResource = keycloak.realm(realm);

        final var response = realmResource.users().create(user);
        if (response.getStatus() != 201) {
            throw new UserNotCreatedException("Failed to create user: " + response.getStatusInfo());
        }

        final var userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        final var userRole = realmResource.roles().get("user").toRepresentation();
        final var userResource = realmResource.users().get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(userRole));
    }
}
