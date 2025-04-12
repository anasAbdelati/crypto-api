package org.projetperso.crypto.glue.server;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class KeycloakManager extends ApiMockServer {

    private static final String BASE = "/admin/realms/anas";

    public KeycloakManager() {
        super(10999);
    }

    public void mockUserCreation() {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("POST")
                        .withPath("/admin/realms/test-realm/users")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(201)
                        .withHeader("Location", "http://localhost:10999/admin/realms/test-realm/users/12345")
        );
    }

    public void mockGetUserRole() {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/admin/realms/test-realm/roles/user")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                {
                  "id": "role-id",
                  "name": "user"
                }
            """)
        );
    }

    public void mockUserRoleAssign() {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("POST")
                        .withPath("/admin/realms/test-realm/users/12345/role-mappings/realm")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(204)
        );
    }

    public void mockTokenRequest() {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("POST")
                        .withPath("/realms/master/protocol/openid-connect/token")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                {
                    "access_token": "fake-token",
                    "expires_in": 300,
                    "token_type": "Bearer"
                }
            """)
        );
    }

    public void mockUserCreationFailure() {
        mockServer.when(
                HttpRequest.request()
                        .withMethod("POST")
                        .withPath(BASE + "/users")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                {
                  "error": "invalid_request",
                  "error_description": "Simulated user creation failure"
                }
            """)
        );
    }

    public void resetExpectations() {
        mockServer.reset();
    }
}

