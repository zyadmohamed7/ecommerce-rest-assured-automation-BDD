@security @idor @regression
Feature: IDOR Security Isolation
  As a customer, I should only be able to interact with my own orders.
  Unauthorized access to other customers' orders should be strictly forbidden.

  Background:
    Given the admin token is available
    And the created item ID is available

  @negative @security
  Scenario: Customer cannot checkout another customer's order
    # Phase 1: User A creates an order
    Given I load credentials for "user1" from users.json
    When I send a POST request to the login endpoint
    Then the customer token is extracted and stored for "user1"
    And the customer username is available
    When the admin sends a POST request to create a new order
    And the created order ID is stored for later use

    # Phase 2: User B tries to checkout User A's order
    When I load credentials for "user2" from users.json
    And I send a POST request to the login endpoint
    And the customer token is extracted and stored for "user2"
    And the customer attempts to checkout the created order
    Then the response status code should be 401
    And the response error message should say "This order does not belong to the provided customer ID"

  @negative @security @bug-caught
  # BUG REPORT: Currently, the API returns ALL orders to any authenticated customer.
  # This scenario correctly fails to demonstrate the lack of data isolation.
  Scenario: Customer cannot see another customer's order in their list
    # Phase 1: User A creates an order
    Given I load credentials for "user1" from users.json
    When I send a POST request to the login endpoint
    Then the customer token is extracted and stored for "user1"
    And the customer username is available
    When the admin sends a POST request to create a new order
    And the created order ID is stored for later use

    # Phase 2: User B views their own orders
    When I load credentials for "user2" from users.json
    And I send a POST request to the login endpoint
    And the customer token is extracted and stored for "user2"
    And the admin sends a GET request to retrieve all orders
    Then the response status code should be 200
    And the response body should not contain the created order ID
