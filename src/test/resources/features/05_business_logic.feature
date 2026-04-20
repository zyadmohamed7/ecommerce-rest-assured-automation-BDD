@business_logic
Feature: Inventory State and Business Logic
  @regression
  Scenario: Creating an order should securely deplete the item stock
    Given the admin token is available
    And the customer token is available
    And the customer username is available
    And the created item ID is available
    When the admin checks the initial item stock
    And the admin sends a POST request to create a new order
    Then the response status code should be 201
    When the admin checks the item stock again
    Then the current stock should be exactly 1 less than the initial stock

  @regression
  Scenario: Deleting an order should restore the item stock fully
    Given the admin token is available
    And the created order ID is available
    And the created item ID is available
    When the admin checks the initial item stock
    And the admin sends a DELETE request for the created order
    Then the response status code should be 204
    When the admin checks the item stock again
    Then the current stock should be exactly 1 more than the initial stock

  @regression
  Scenario: Customers cannot order more stock than exists
    Given the admin token is available
    And the customer token is available
    And the customer username is available
    And the created item ID is available
    When the admin maliciously sends an order for 1000 copies of the item
    Then the response status code should be 400
    And the response error message should say "Not enough stock"
