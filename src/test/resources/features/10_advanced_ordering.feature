@ordering @regression
Feature: Advanced Ordering Logic
  As a customer, I want to ensure my order totals are calculated correctly and stock is managed properly.

  Background:
    Given I load credentials for "admin" from users.json
    And I send a POST request to the login endpoint
    Then the admin token is extracted and stored
    When the admin sends a POST request to create a new item with random data
    Then the created item ID is stored for later use
    And the item price is stored for later use
    And I load credentials for "user1" from users.json
    When I send a POST request to the login endpoint
    Then the customer token is extracted and stored for "user1"

  @smoke
  Scenario: Order total must match quantity * item price
    When the admin creates an order for the customer with 5 copies
    Then the response status code should be 201
    # Customer token is set inside verification steps or we set it manually
    And the order total should be correct based on item price and quantity

  @negative @boundaries
  Scenario: Cannot order 0 items
    When the admin creates an order for the customer with 0 copies
    Then the response status code should be 400
    And the response error message should say "Quantity must be at least 1."

  @negative @stock
  Scenario: Cannot order more items than currently in stock
    # Ordering 1001 should safely exceed any random stock generated (1-100)
    When the admin creates an order for the customer with 1001 copies
    Then the response status code should be 400
    And the response error message should say "Not enough stock"
