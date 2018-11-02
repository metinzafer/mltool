Feature: MainFeature
  This feature deals with the microservices
	@Main
  Scenario: microservice check
    Given set name as "metin"
    And set surname as "zafer"
    And set adress as smart
      | country | city   |
      | ireland | dublin |
    And set adress as table
      | country | ireland |
      | city    | dublin  |
    Then i saw fullname
 