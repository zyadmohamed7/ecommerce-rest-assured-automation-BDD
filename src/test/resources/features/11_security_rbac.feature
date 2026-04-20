@rbac @security @regression
Feature: Role-Based Access Control (RBAC)
  As a security-conscious system, I must ensure that customers cannot perform admin-only actions.

  Background:
    # Set up symbols for both users
    Given I load credentials for "admin" from users.json
    And I send a POST request to the login endpoint
    Then the admin token is extracted and stored
    
    Given I load credentials for "user1" from users.json
    And I send a POST request to the login endpoint
    Then the customer token is extracted and stored for "user1"

  @negative
  Scenario: Regular customer cannot delete an order
    # Phase 1: Admin creates an order
    Given the admin token is available
    When the admin sends a POST request to create a new item with random data
    Then the created item ID is stored for later use
    When the admin sends a POST request to create a new order
    And the created order ID is stored for later use

    # Phase 2: Customer tries to delete it
    When the customer attempts to send a DELETE request for the created order
    Then the response status code should be 403
    And the response body should contain "Forbidden: customer does not have access."

  @positive
  Scenario: Admin can delete an order
    # Phase 1: Create order
    Given the admin token is available
    When the admin sends a POST request to create a new item with random data
    Then the created item ID is stored for later use
    When the admin sends a POST request to create a new order
    And the created order ID is stored for later use
    
    # Phase 2: Admin deletes it
    When the admin sends a DELETE request for the created order
    Then the response status code should be 204
