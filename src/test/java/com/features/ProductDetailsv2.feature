##Added a second feature file just to test parallel execution

@ProductDetails @OnTop
Feature: Product Details
  Validate the details page for a product

  @HighPriority @Smoke
  Scenario: Compare Product Details on Amazon v2
    Given I am on the Amazon home page
    And I search for the "iPhone" product
    And I select the first entry in the search list
    Then I verify that the product URL, title, price, rating, and description are displayed accurately on the details page
    When I go back to the search results for the same product
    And I select the second entry in the search list
    Then I verify that the second product detail page is different from the first one