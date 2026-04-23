@login-val @regression
Feature: Login Input Validation
  As a user, I want to ensure that my login attempts are strictly validated as per the documentation.

  @negative
  Scenario Outline: Login with invalid field constraints
    When the user attempts to login with username "<username>" and password "<password>"
    Then the response status code should be 400
    And the response should contain the error list '<errors>'

    Examples:
      | username | password | errors                                              |
      | NULL     | 123456   | "username" must be a string                        |
      | user1    | NULL     | "password" must be a string                        |
      | user1    | 123      | Password should have a minimum length of 6.        |
      | a        | 123456   | Username should have a minimum length of 3.        |
      | !@#$%    | 123456   | Username must contain only alphanumeric characters. |

  @negative
  Scenario: Login with invalid credentials
    When the user attempts to login with username "wronguser" and password "wrongpassword"
    Then the response status code should be 401
    And the response error message should say "Invalid credentials"
