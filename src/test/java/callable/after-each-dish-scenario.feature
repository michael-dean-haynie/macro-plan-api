Feature: After each scenario delete the test data
	See the "before-each-dish-scenario.feature" for where these foods are getting created first
	(NOTE: none of this shows up in the reports!!! It's a limitation of the afterScenario stuff)

	Background:
		* url baseUrl

	Scenario: Delete the test data
		# Delete dish templates (Must happen before food-templates or fk constraint issues happen)
		* path 'dish', prepData.tunaSW.id
		* method delete
		* status 200
		
		* path 'dish', prepData.hamAndTurkeySW.id
		* method delete
		* status 200
	
		# Delete food templates
		* path 'food', prepData.tuna.id
		* method delete
		* status 200
		
		* path 'food', prepData.celery.id
		* method delete
		* status 200
		
		* path 'food', prepData.mayo.id
		* method delete
		* status 200
		
		* path 'food', prepData.relish.id
		* method delete
		* status 200
		
		* path 'food', prepData.bread.id
		* method delete
		* status 200
		
		* path 'food', prepData.turkey.id
		* method delete
		* status 200
		
		* path 'food', prepData.ham.id
		* method delete
		* status 200