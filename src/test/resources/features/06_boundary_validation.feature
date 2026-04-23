@boundaries
Feature: Boundary and Negative Payload Validation

  Background:
    Given the admin token is available

  @regression
  Scenario: Cannot create an item with negative stock
    When the admin maliciously attempts to create an item with a stock of -1
    Then the response status code should be 400

  @regression
  Scenario: Cannot create an item with negative price
    When the admin maliciously attempts to create an item with a price of -10.50
    Then the response status code should be 400

  @regression
  Scenario: Cannot checkout the exact same order twice
    Given the customer token is available
    And the customer username is available
    And the created item ID is available
    When the admin sends a POST request to create a new order
    And the created order ID is stored for later use
    And the customer attempts to checkout the created order
    Then the response status code should be 200
    When the customer accidentally attempts to checkout the same order twice
    Then the response status code should be 400
    And the response error message should say "already been paid"
