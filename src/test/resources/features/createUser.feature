Feature: Create User and Get User from API Services

  Scenario: Make sure the user is created before fetching
    Given the User can create a new user

  Scenario Outline: Verify that a new user can be retrieved
    Given the User can fetch the created user "<username>"

    Examples:
      | username   |
      | RichieRich |
      | William    |