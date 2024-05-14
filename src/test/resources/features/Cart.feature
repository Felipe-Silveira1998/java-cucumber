Feature: Cart

  Background:
    Given that access "${sauceDemo::url}"
    And log in with "standard"

  Scenario: Adding item to cart
    When add an item to cart
    Then the cart icon will show the quantity of items in the cart

  Scenario: Added item is present in cart
    When add an item to cart
    And access the shopping cart
    Then the added product will be in the cart

  Scenario: Remove item to cart
    And add an item to cart
    And access the shopping cart
    When Remove item to cart
    Then cart will be empty

  Scenario: Correct redirection when clicking continue shopping
    And access the shopping cart
    When click continue shopping
    Then i am redirected to the home product page