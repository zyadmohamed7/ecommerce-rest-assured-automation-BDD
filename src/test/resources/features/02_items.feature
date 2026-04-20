@items
Feature: Item Management

  @smoke
  Scenario: Admin creates a new item successfully
    Given the admin token is available
    When the admin sends a POST request to create a new item with random data
    And the response status code should be 201
    And the response body matches the "items" schema
    And the created item ID is stored for later use

  @smoke
  Scenario: Admin retrieves an item by ID successfully
    Given the admin token is available
    And the created item ID is available
    When the admin sends a GET request for the created item
    Then the response status code should be 200

  @smoke
  Scenario: Admin updates an item successfully
    Given the admin token is available
    And the created item ID is available
    When the admin sends a PUT request to update the created item with random data
    Then the response status code should be 200
    And the updated item data is reflected in the response

