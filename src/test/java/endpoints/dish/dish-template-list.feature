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
		
#	Scenario Outline: Successfully get list of food templates sorted by the "<sortField>" field <sortDirection>
#		# Create food template 1
#		* path 'food'
#		* def payload = read('classpath:endpoints/food/json/food-template.json')
#		* set payload.<sortField> = '1'
#		* request payload
#		* method post
#		* status 200
#		* def food1Id = response.id
#		
#		# Create food template 2
#		* path 'food'
#		* def payload = read('classpath:endpoints/food/json/food-template.json')
#		* set payload.<sortField> = '2'
#		* request payload
#		* method post
#		* status 200
#		* def food2Id = response.id
#		
#		# Search for food templates that were just created
#		* path 'food'
#		* param searchTerm = ''
#		* param sortField = '<sortField>'
#		* param sortDirection = '<sortDirection>'
#		When method get
#		Then status 200
#		* def ids = get response[*].id
#		* match ids contains food1Id
#		* match ids contains food2Id
#		
#		# Assert proper sort order
#		* def fun = function(x){ return x.id == food1Id || x.id == food2Id }
#		* def filteredResponse = karate.filter(response, fun)
#		* assert filteredResponse[<food1Index>].id == food1Id
#		* assert filteredResponse[<food2Index>].id == food2Id
#		
#		# Delete food templates that were just created
#		* path 'food', food1Id
#		* method delete
#		* status 200
#		* path 'food', food2Id
#		* method delete
#		* status 200
#		
#		Examples: 
#		| sortField     | sortDirection | food1Index | food2Index |
#		| calories      | ASC           | 0          | 1          |
#		| calories      | DESC          | 1          | 0          |
#		| fat           | ASC           | 0          | 1          |
#		| fat           | DESC          | 1          | 0          |
#		| carbs         | ASC           | 0          | 1          |
#		| carbs         | DESC          | 1          | 0          |
#		| protein       | ASC           | 0          | 1          |
#		| protein       | DESC          | 1          | 0          |
#		| name          | ASC           | 0          | 1          |
#		| name          | DESC          | 1          | 0          |
#		| brand         | ASC           | 0          | 1          |
#		| brand         | DESC          | 1          | 0          |
#		| styleOrFlavor | ASC           | 0          | 1          |
#		| styleOrFlavor | DESC          | 1          | 0          |
#	
#	Scenario Outline: Successfully get list of food templates sorted by the calculated field "<calculatedField>" <sortDirection>
#		# Create food template 1
#		* path 'food'
#		* def payload = read('classpath:endpoints/food/json/food-template.json')
#		* set payload.fat = <food1Fat>
#		* set payload.carbs = <food1Carbs>
#		* set payload.protein = <food1Protein>
#		* request payload
#		* method post
#		* status 200
#		* def food1Id = response.id
#		
#		# Create food template 2
#		* path 'food'
#		* def payload = read('classpath:endpoints/food/json/food-template.json')
#		* set payload.fat = <food2Fat>
#		* set payload.carbs = <food2Carbs>
#		* set payload.protein = <food2Protein>
#		* request payload
#		* method post
#		* status 200
#		* def food2Id = response.id
#		
#		# Search for food templates that were just created
#		* path 'food'
#		* param searchTerm = ''
#		* param sortField = '<calculatedField>'
#		* param sortDirection = '<sortDirection>'
#		When method get
#		Then status 200
#		* def ids = get response[*].id
#		* match ids contains food1Id
#		* match ids contains food2Id
#		
#		# Assert proper sort order
#		* def fun = function(x){ return x.id == food1Id || x.id == food2Id }
#		* def filteredResponse = karate.filter(response, fun)
#		* print filteredResponse
#		* assert filteredResponse[<food1Index>].id == food1Id
#		* assert filteredResponse[<food2Index>].id == food2Id
#		
#		# Delete food templates that were just created
#		* path 'food', food1Id
#		* method delete
#		* status 200
#		* path 'food', food2Id
#		* method delete
#		* status 200
#		
#		Examples: 
#		| calculatedField   | sortDirection | food1Fat | food1Carbs | food1Protein | food2Fat | food2Carbs | food2Protein | food1Index | food2Index |
#		| fatPercentage     | ASC           | 1        | 1          | 1            | 2        | 1          | 1            | 0          | 1          |
#		| fatPercentage     | DESC          | 1        | 1          | 1            | 2        | 1          | 1            | 1          | 0          |
#		| carbsPercentage   | ASC           | 1        | 1          | 1            | 1        | 2          | 1            | 0          | 1          |
#		| carbsPercentage   | DESC          | 1        | 1          | 1            | 1        | 2          | 1            | 1          | 0          |
#		| proteinPercentage | ASC           | 1        | 1          | 1            | 1        | 1          | 2            | 0          | 1          |
#		| proteinPercentage | DESC          | 1        | 1          | 1            | 1        | 1          | 2            | 1          | 0          |
#		
#		
#		
