@orders
Feature: Order Management

  @smoke
  Scenario: Admin creates a new order successfully
    Given the admin token is available
    And the created item ID is available
    And the customer username is available
    When the admin sends a POST request to create a new order
    And the response status code should be 201
    And the response body matches the "orders" schema
    And the created order ID is stored for later use

  @smoke
  Scenario: Admin retrieves all orders successfully
    Given the admin token is available
    When the admin sends a GET request to retrieve all orders
    Then the response status code should be 200

  @smoke
  Scenario: Customer checks out an order successfully
    Given the customer token is available
    And the created order ID is available
    When the customer sends a GET request to checkout the order
    Then the response status code should be 200

  @smoke
  Scenario: Admin retrieves all paid orders successfully
    Given the admin token is available
    When the admin sends a GET request to retrieve all paid orders
    Then the response status code should be 200
    And all returned orders should have paid status

