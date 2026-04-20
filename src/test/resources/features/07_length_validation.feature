@length @regression
Feature: Specific Length Constraints Validation
  As an admin, I want to ensure that item names and descriptions conform to the documentation's limits.

  Background:
    Given the admin token is available

  @negative
  Scenario: Item name is too short
    When the admin maliciously attempts to create an item with a name of 2 characters
    Then the response status code should be 400

  @positive
  Scenario: Item name is at the maximum limit
    When the admin maliciously attempts to create an item with a name of 100 characters
    Then the response status code should be 201

  @negative
  Scenario: Item name is over the maximum limit
    When the admin maliciously attempts to create an item with a name of 101 characters
    Then the response status code should be 400

  @negative
  Scenario: Item description is too short
    When the admin maliciously attempts to create an item with a description of 4 characters
    Then the response status code should be 400

  @positive
  Scenario: Item description is at the maximum limit
    When the admin maliciously attempts to create an item with a description of 500 characters
    Then the response status code should be 201

  @negative
  Scenario: Item description is over the maximum limit
    When the admin maliciously attempts to create an item with a description of 501 characters
    Then the response status code should be 400
