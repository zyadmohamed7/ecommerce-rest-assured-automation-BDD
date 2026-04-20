@cleanup
Feature: Cleanup

  @smoke
  Scenario: Delete order
    Given the admin token is available
    And the created order ID is available
    When the admin sends a DELETE request for the created order
    Then the response status code should be 204 or 404

  @smoke
  Scenario: Delete item
    Given the admin token is available
    And the created item ID is available
    When the admin sends a DELETE request for the created item
    Then the response status code should be 204 or 404