Feature: Login/Logout

  Background:
    Given that access "${sauceDemo::url}"

  Scenario: Log in successfully
    When log in with "standard"
    Then i am redirected to home page

  Scenario: Trying to Login with an Invalid User
    When log in with "invalidUser"
    Then login error message appears "Epic sadface: Username and password do not match any user in this service"

  Scenario: Trying to Login with an Invalid Password
    When log in with "invalidPassword"
    Then login error message appears "Epic sadface: Username and password do not match any user in this service"

  Scenario: Trying to Login with a Non-existent User
    When log in with "non_Existent-User"
    Then login error message appears "Epic sadface: Username and password do not match any user in this service"

  Scenario: Error when trying to log in without username and password filled in
    When click login
    Then login error message appears "Epic sadface: Username is required"

  Scenario: Error when trying to login without username filled in
    When try to log in without username filled in
    Then login error message appears "Epic sadface: Username is required"

  Scenario: Error when trying to log in without a password filled in
    When try to log in without password filled in
    Then login error message appears "Epic sadface: Password is required"

  Scenario: Error when trying to log in with a blocked user
    When log in with "blockedUser"
    Then login error message appears "Epic sadface: Sorry, this user has been locked out."

  Scenario: Logout successfully
    And log in with "standard"
    And click on options
    When click logout
    Then i am redirected to login page