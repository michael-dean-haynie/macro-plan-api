Feature: Test the /dish list endpoints

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

	Scenario Outline: Successfully get list of dish templates matching search term "<searchTerm>" in various fields		
		# Search for dish templates
		* path 'dish'
		* param searchTerm = <searchTerm>
		* param sortField = 'name'
		* param sortDirection = ''
		When method get
		Then status 200
		* def ids = get response[*].id
		* match ids <containsTunaId> prepData.tunaSW.id
		* match ids <containsHamAndTurkeyId> prepData.hamAndTurkeySW.id
		
		Examples: 
		| searchTerm                | containsTunaId | containsHamAndTurkeyId |
		
		# testing search against dishTemplate.name
		| 'Ham & Turkey Sandwich'   | !contains      | contains               |
		| 'Tuna Sandwich'           |  contains      | !contains              |
		| 'afksjfvsvvnvnnnnwla'     | !contains      | !contains              |
		
		# testing search against dishTemplate.ingredients[*].food.name
		| 'Black'                   | !contains      | contains               |
		| 'Celery'                  |  contains      | !contains              |
		| 'Sandwich Bread'          |  contains      | contains               |
		
		# testing search against dishTemplate.ingredients[*].food.brand
		| 'Hillshire'               | !contains      | contains               |
		| 'Kraft'                   |  contains      | !contains              |
		| "Nature's Own"            |  contains      | contains               |
		
		# testing search against dishTemplate.ingredients[*].food.styleOrFlavor
		| 'Ultra Thin'              | !contains      | contains               |
		| 'Chunk Light'             |  contains      | !contains              |
		| "100% Whole Grain"        |  contains      | contains               |
		
	Scenario Outline: Successfully get list of food templates sorted by the "<sortField>" field <sortDirection>
		
		# Get list of dish templates
		* path 'dish'
		* param searchTerm = ''
		* param sortField = '<sortField>'
		* param sortDirection = '<sortDirection>'
		When method get
		Then status 200
		* def ids = get response[*].id
		* match ids contains prepData.tunaSW.id
		* match ids contains prepData.hamAndTurkeySW.id
		
		# Assert proper sort order
		* def fun = function(x){ return x.id == prepData.tunaSW.id || x.id == prepData.hamAndTurkeySW.id }
		* def filteredResponse = karate.filter(response, fun)
		* assert filteredResponse[<tunaIndex>].id == prepData.tunaSW.id
		* assert filteredResponse[<hamAndTurkeyIndex>].id == prepData.hamAndTurkeySW.id
		
		Examples: 
		| sortField         | sortDirection | tunaIndex  | hamAndTurkeyIndex |
		| name              | ASC           | 1          | 0                 |
		| name              | DESC          | 0          | 1                 |
		| calories          | ASC           | 1          | 0                 |
		| calories          | DESC          | 0          | 1                 |
		| fat               | ASC           | 1          | 0                 |
		| fat               | DESC          | 0          | 1                 |
		| carbs             | ASC           | 0          | 1                 |
		| carbs             | DESC          | 1          | 0                 |
		| protein           | ASC           | 0          | 1                 |
		| protein           | DESC          | 1          | 0                 |
		| fatPercentage     | ASC           | 1          | 0                 |
		| fatPercentage     | DESC          | 0          | 1                 |
		| carbsPercentage   | ASC           | 0          | 1                 |
		| carbsPercentage   | DESC          | 1          | 0                 |
		| proteinPercentage | ASC           | 0          | 1                 |
		| proteinPercentage | DESC          | 1          | 0                 |


