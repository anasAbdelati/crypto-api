Feature: Alert API

  Background:
  Given the following alerts exist for the user:
  | userId | coinId  | type      | active |
  | user   | bitcoin | RECURRING | true   |
  | user   | eth     | THRESHOLD | true   |
  | user   | test    | THRESHOLD | true   |
  | user   | tost    | THRESHOLD | true   |

  Scenario: Get list of all alerts for the user
    Given a user is authenticated with the role 'user'
    When the user gets the alert list
    Then a 'OK' status code is returned
    And the following alerts are returned:
      | id | userId | coinId  | type      | active |
      | 1  | user   | bitcoin | RECURRING | true   |
      | 2  | user   | eth     | THRESHOLD | true   |
      | 3  | user   | test    | THRESHOLD | true   |
      | 4  | user   | tost    | THRESHOLD | true   |

  Scenario: Get a specific alert by ID
    Given a user is authenticated with the role 'user'
    When the user gets the alert with id 4
    Then a 'OK' status code is returned
    And the following alert is returned:
      | id | userId | coinId | type      | active |
      | 4  | user   | tost   | THRESHOLD | true   |

  Scenario: Get a non-existing alert
    Given a user is authenticated with the role 'user'
    When the user gets the alert with id 9999
    Then a 'BAD_REQUEST' status code is returned

  Scenario: Delete a user alert
    Given a user is authenticated with the role 'user'
    When the user deletes the alert with id 1
    Then a 'OK' status code is returned

  Scenario: Delete a non-existing alert
    Given a user is authenticated with the role 'user'
    When the user deletes the alert with id 8888
    Then a 'BAD_REQUEST' status code is returned
