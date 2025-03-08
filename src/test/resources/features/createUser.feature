Feature: Create User and Get User from API Services

  Scenario: Make sure the user is created before fetching
    Given the User can create a new user with expected status code 200

  Scenario Outline: Verify that a new user can be retrieved
    Given the User can fetch the created user "<username>" with expected status code 200

    Examples:
      | username   |
      | RichieRich |
      | William    |

  # Negative scenarios
  Scenario: Create user with missing required field
    Given the user create user with missing body and return 500

    Scenario: Create user with incorrect body
      Given the user create user with incorrect body and return 400