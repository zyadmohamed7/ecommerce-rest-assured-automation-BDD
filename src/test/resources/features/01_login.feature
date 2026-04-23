@login
Feature: Login API

  As an API consumer
  I want to authenticate via the login endpoint
  So that I can receive a valid token for subsequent requests

  @smoke @regression
  Scenario: Admin logs in successfully
    Given I load credentials for "admin" from users.json
    When I send a POST request to the login endpoint
    Then the response status code should be 200
    And the admin token is extracted and stored

  @smoke @regression
  Scenario Outline: All customer users login successfully
    Given I load credentials for "<username>" from users.json
    When I send a POST request to the login endpoint
    Then the response status code should be 200
    And the customer token is extracted and stored for "<username>"

    Examples:
      | username |
      | user1    |
      | user2    |
      | user3    |
      | user4    |