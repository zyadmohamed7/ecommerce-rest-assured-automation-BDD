@security
Feature: Role-Based Access Control (RBAC) Validation
  @regression
  Scenario: A customer cannot cheat the system to create items
    Given the customer token is available
    When the customer attempts to send a POST request to create an item
    Then the response status code should be 403

  @regression
  Scenario: A customer cannot delete an existing order
    Given the customer token is available
    And the created order ID is available
    When the customer attempts to send a DELETE request for the created order
    Then the response status code should be 403

  @regression
  Scenario: An admin cannot checkout a customer's order
    Given the admin token is available
    And the created order ID is available
    When the admin attempts to checkout the created order
    Then the response status code should be 403
