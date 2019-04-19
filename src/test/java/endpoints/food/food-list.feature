Feature: Test the food list endpoints

	Background:
		* url baseUrl

	Scenario Outline: Successfully get list of foods matching search term in the "<fieldName>" field
		# Create food 1
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.<fieldName> = 'testing_search_1'
		* request payload
		* method post
		* status 200
		* def food1Id = response.id
		
		# Create food 2
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.<fieldName> = 'testing_search_2'
		* request payload
		* method post
		* status 200
		* def food2Id = response.id
		
		# Search for foods that were just created
		* path 'food'
		* param searchTerm = 'testing_search'
		* param sortField = 'name'
		* param sortDirection = ''
		When method get
		Then status 200
		* def ids = get response[*].id
		* match ids contains food1Id
		* match ids contains food2Id
		
		# Delete foods that were just created
		* path 'food', food1Id
		* method delete
		* status 200
		* path 'food', food2Id
		* method delete
		* status 200
		
		Examples: 
		| fieldName     |
		| name          |
		| brand         |
		| styleOrFlavor |
		
	Scenario Outline: Successfully get list of foods sorted by the "<sortField>" field <sortDirection>
		# Create food 1
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.<sortField> = '1'
		* request payload
		* method post
		* status 200
		* def food1Id = response.id
		
		# Create food 2
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.<sortField> = '2'
		* request payload
		* method post
		* status 200
		* def food2Id = response.id
		
		# Search for foods that were just created
		* path 'food'
		* param searchTerm = ''
		* param sortField = '<sortField>'
		* param sortDirection = '<sortDirection>'
		When method get
		Then status 200
		* def ids = get response[*].id
		* match ids contains food1Id
		* match ids contains food2Id
		
		# Assert proper sort order
		* def fun = function(x){ return x.id == food1Id || x.id == food2Id }
		* def filteredResponse = karate.filter(response, fun)
		* print filteredResponse
		* assert filteredResponse[<food1Index>].id == food1Id
		* assert filteredResponse[<food2Index>].id == food2Id
		
		# Delete foods that were just created
		* path 'food', food1Id
		* method delete
		* status 200
		* path 'food', food2Id
		* method delete
		* status 200
		
		Examples: 
		| sortField     | sortDirection | food1Index | food2Index |
		| calories      | ASC           | 0          | 1          |
		| calories      | DESC          | 1          | 0          |
		| fat           | ASC           | 0          | 1          |
		| fat           | DESC          | 1          | 0          |
		| carbs         | ASC           | 0          | 1          |
		| carbs         | DESC          | 1          | 0          |
		| protein       | ASC           | 0          | 1          |
		| protein       | DESC          | 1          | 0          |
		| name          | ASC           | 0          | 1          |
		| name          | DESC          | 1          | 0          |
		| brand         | ASC           | 0          | 1          |
		| brand         | DESC          | 1          | 0          |
		| styleOrFlavor | ASC           | 0          | 1          |
		| styleOrFlavor | DESC          | 1          | 0          |
	
	Scenario Outline: Successfully get list of foods sorted by the calculated field "<calculatedField>" <sortDirection>
		# Create food 1
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.fat = <food1Fat>
		* set payload.carbs = <food1Carbs>
		* set payload.protein = <food1Protein>
		* request payload
		* method post
		* status 200
		* def food1Id = response.id
		
		# Create food 2
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.fat = <food2Fat>
		* set payload.carbs = <food2Carbs>
		* set payload.protein = <food2Protein>
		* request payload
		* method post
		* status 200
		* def food2Id = response.id
		
		# Search for foods that were just created
		* path 'food'
		* param searchTerm = ''
		* param sortField = '<calculatedField>'
		* param sortDirection = '<sortDirection>'
		When method get
		Then status 200
		* def ids = get response[*].id
		* match ids contains food1Id
		* match ids contains food2Id
		
		# Assert proper sort order
		* def fun = function(x){ return x.id == food1Id || x.id == food2Id }
		* def filteredResponse = karate.filter(response, fun)
		* print filteredResponse
		* assert filteredResponse[<food1Index>].id == food1Id
		* assert filteredResponse[<food2Index>].id == food2Id
		
		# Delete foods that were just created
		* path 'food', food1Id
		* method delete
		* status 200
		* path 'food', food2Id
		* method delete
		* status 200
		
		Examples: 
		| calculatedField   | sortDirection | food1Fat | food1Carbs | food1Protein | food2Fat | food2Carbs | food2Protein | food1Index | food2Index |
		| fatPercentage     | ASC           | 1        | 1          | 1            | 2        | 1          | 1            | 0          | 1          |
		| fatPercentage     | DESC          | 1        | 1          | 1            | 2        | 1          | 1            | 1          | 0          |
		| carbsPercentage   | ASC           | 1        | 1          | 1            | 1        | 2          | 1            | 0          | 1          |
		| carbsPercentage   | DESC          | 1        | 1          | 1            | 1        | 2          | 1            | 1          | 0          |
		| proteinPercentage | ASC           | 1        | 1          | 1            | 1        | 1          | 2            | 0          | 1          |
		| proteinPercentage | DESC          | 1        | 1          | 1            | 1        | 1          | 2            | 1          | 0          |
		
	Scenario: Successfully get list of template foods only
		# Create template food
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.name = 'testing_only_return_templates'
		* request payload
		* method post
		* status 200
		* def food1Id = response.id
		
		# Create non-template food
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/non-template-food.json')
		* set payload.name = 'testing_only_return_templates'
		* request payload
		* method post
		* status 200
		* def food2Id = response.id
		
		# Get only the template food
		* path 'food'
		* param searchTerm = 'testing_only_return_templates'
		* param sortField = 'name'
		* param sortDirection = ''
		When method get
		Then status 200
		* match response[*].id contains food1Id
		* match response[*].isTemplate !contains false
		
		# Delete foods that were just created
		* path 'food', food1Id
		* method delete
		* status 200
		* path 'food', food2Id
		* method delete
		* status 200
		
		
		
