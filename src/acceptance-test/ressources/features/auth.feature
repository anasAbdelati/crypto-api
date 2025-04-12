Feature: User Registration

  Background:
    Given keycloak is running

  Scenario: Successful user registration
    Given Keycloak allows user registration
    When I register a user with:
      | username | password  | first | last | email            |
      | johndoe  | secret123 | John  | Doe  | john@example.com |
    Then a 'OK' status code is returned

  Scenario: Registration fails due to Keycloak error
    Given Keycloak rejects user registration
    When I register a user with:
      | username | password | first | last | email           |
      | janedoe  | fail123  | Jane  | Doe  | jane@example.com |
    Then a 'BAD_REQUEST' status code is returned

