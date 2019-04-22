Feature: Test the /dish endpoints. These endpoints should only directly modify dish templates

	Background:
		
		# Before each scenario create some test data to use while testing dishes endpoints
		* def prepData = call read('classpath:callable/before-each-dish-scenario.feature')
		
		# After each scenario delete the test data
		* configure afterScenario = 
		"""
		function(){
		  karate.call('classpath:callable/after-each-dish-scenario.feature');
		}
		"""

		* url baseUrl
		
	Scenario: Successfully create a dish template
		# Actual creation happens in 'before-each-dish-scenario.feature'. Cause other scenarios need it too.
		# Here we're just asserting stuff.
		
		