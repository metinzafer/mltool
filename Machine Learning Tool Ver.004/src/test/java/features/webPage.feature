Feature: WebPageTest
  This feature deals with the web site test

  @Web
  Scenario: web login page test
    Given I am on the zoo site
    When I click on "Adoption"
    Then I set the start date to "Today"
    Then I close the browser

  @Web
  Scenario: to populate the contact form
    Given I am on the zoo site
    When I click on "Contact"
    Then I populate the contact form
    Then I close the browser
