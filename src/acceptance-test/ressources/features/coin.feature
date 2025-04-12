Feature: Coin API

  Background:
    Given coinGeckoApi is running

  Scenario: Retrieve details of a specific coin
    Given the following coin exists:
      | id      | name     | symbol |
      | bitcoin | Bitcoin  | btc    |
    And a user is authenticated with the role 'user'
    When the user gets the coin 'bitcoin'
    Then a 'OK' status code is returned
    And the following coin is returned
      | id      | name     | symbol |
      | bitcoin | Bitcoin  | btc    |

  Scenario: Retrieve list of top coins
    Given the following coins exists:
      | id       | name      | symbol |
      | bitcoin  | Bitcoin   | btc    |
      | ethereum | Ethereum  | eth    |
    And a user is authenticated with the role 'user'
    When the user gets the coin list
    Then a 'OK' status code is returned
    And the following coins are returned
      | id       | name      | symbol |
      | bitcoin  | Bitcoin   | btc    |
      | ethereum | Ethereum  | eth    |

  Scenario: Subscribe to coin with recurring alert
    Given the following coin exists:
      | id       | name     | symbol |
      | ethereum | Ethereum | eth    |
    And a user is authenticated with the role 'user'
    When the user subscribes to recurring alerts for coin 'ethereum' with time 50
    Then a 'OK' status code is returned

  Scenario: Subscribe to coin with threshold alert
    Given the following coin exists:
      | id       | name     | symbol |
      | ethereum | Ethereum | eth    |
    And a user is authenticated with the role 'user'
    When the user subscribes to threshold alerts for coin 'ethereum' with target price 3000
    Then a 'OK' status code is returned


  Scenario: Coin not found
    Given 'bitcoin' doesn't exist
    And a user is authenticated with the role 'user'
    When the user gets the coin "bitcoin"
    Then  a 'BAD_REQUEST' status code is returned

  Scenario: Retrieve details of a specific coin with wrong user role
    Given the following coin exists:
      | id      | name     | symbol |
      | bitcoin | Bitcoin  | btc    |
    And a user is authenticated with the role 'yhy'
    When the user gets the coin 'bitcoin'
    Then a 'FORBIDDEN' status code is returned

