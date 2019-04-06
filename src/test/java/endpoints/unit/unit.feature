Feature: Test the unit endpoints

  Scenario: Get all units
		Given url 'http://localhost:8080/unit'
		When method get
		Then status 200
		And match response == '#[15] schemas.unit'
		