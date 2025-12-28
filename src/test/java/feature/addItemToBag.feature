
Feature: Add Item to Bag

  Background:
    Given User navigates to Macy's landing page

  @AddProductToBag
  Scenario: Add a single product to bag
    When User enters product name "nike"
    And User chooses category "Sneakers"
    And User selects the product and adds it to the bag
    Then User checks the added items in bag

  @AddProductAndCheckout
  Scenario: Add multiple products and checkout
    When User enters product name "nike"
    And User chooses category "Sneakers"
    And User selects the product and adds it to the bag
    Then User checks the added items in bag
    And User checks out to payment page
    And User enters shipping and payment details
