Feature: Create User and Get User from API Services

  Background: Verify the user is able to create and retrieve user
    Given the User can create a new user

  Scenario: Verify that a new user can be created and retrieved
    Given the User can fetch the created user "William"