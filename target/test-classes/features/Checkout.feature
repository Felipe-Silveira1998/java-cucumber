Feature: Cart

  Background:
    Given that access "${sauceDemo::url}"
    And log in with "standard"
    When add an item to cart
    And access the shopping cart
    When click checkout

  Scenario: Correct redirection when clicking checkout
    Then i am redirected to checkout page

  Scenario: When you click on cancel you must return to the cart screen
    When click cancel
    Then i am redirected to cart page

  Scenario: Error when not filling in any checkout information
    When click continue
    Then error message is displayed "Error: First Name is required"

  Scenario: Error when not filling in the first name
    And fill in only the last name and zip code
    When click continue
    Then error message is displayed "Error: First Name is required"

  Scenario: Error when not filling in the last name
    And fill in only the first name and zip code
    When click continue
    Then error message is displayed "Error: Last Name is required"

  Scenario: Error when not filling in the zip code
    And fill in only the first name and last name
    When click continue
    Then error message is displayed "Error: Postal Code is required"

  Scenario: Order details
    And fill delivery information
    When click continue
    Then redirected to order details