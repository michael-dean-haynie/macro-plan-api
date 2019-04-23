Feature: Test the /food endpoints. These endpoints should only directly modify food templates

	Background:
		* url baseUrl
		
	# --------------------------------------------
	# CREATE
	# --------------------------------------------

	Scenario: Successfully create a food template using existing unit. Validate schema and values.
		#	Get units	 
		* path 'unit'
		* method get
		* def units = response
		
		# Create a food temlate
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/food-template.json')
		* set payload.measurements = [{unit: '#(units[0])', amount: 42}]
		Given request payload
		When method post
		Then status 200
		
		# Validate schema
		* match response == schemas.foodTemplate
		
		# Validate Values
		* assert response.calories == payload.calories
		* assert response.fat == payload.fat
		* assert response.carbs == payload.carbs
		* assert response.protein == payload.protein
		* assert response.name == payload.name
		* assert response.brand == payload.brand
		* assert response.styleOrFlavor == payload.styleOrFlavor
		* assert response.measurements[0].unit.id == payload.measurements[0].unit.id
		* assert response.measurements[0].amount == payload.measurements[0].amount
		* assert response.isTemplate == payload.isTemplate
		
		# Delete food template that was just created
		* path 'food', response.id
		* method delete
		* status 200
		
	Scenario Outline: Fail to create food template due invalid field (<fieldName> = <value>)
		* def payload = read('classpath:endpoints/food/json/food-template.json')
		* set payload.<fieldName> = <value>
		* path 'food'
		Given request payload
		When method post
		Then status 400
		
		Examples:
		| fieldName                          | value |
		| calories                           | null  |
		| fat                                | null  |
		| carbs                              | null  |
		| protein                            | null  |
		| name                               | null  |
		| name                               | ''    |
		| name                               | ' '   |
		| measurements[0].unit.id            | null  |
		| measurements[0].unit.unitSystem    | null  |
		| measurements[0].unit.unitType      | null  |
		| measurements[0].unit.unit          | null  |
		| measurements[0].unit.unitTypeRatio | null  |
		| measurements                       | null  |
		| measurements                       | []    |
		| isTemplate                         | null  |
		
	# --------------------------------------------
	# READ
	# --------------------------------------------
	
	Scenario: Successfully get a food template
		# First, create a food template
		* path 'food'
		* request read('classpath:endpoints/food/json/food-template.json')
		* method post
		* status 200
	
		# Get same food template that was just created
		Given path 'food', response.id
		When method get
		Then status 200
		And match response == schemas.foodTemplate
		
		# Delete food template that was just created
		* path 'food', response.id
		* method delete
		* status 200
		
	Scenario: Fail to get a food template that does not exist
		Given path 'food', 999999999
		When method get
		Then status 404
		
	# --------------------------------------------
	# UPDATE
	# --------------------------------------------
		
	Scenario Outline: Fail or succeed to update a food template (set payload.<fieldName> = <value> and expect <status>)
		# Create a food template
		* path 'food'
		* request read('classpath:endpoints/food/json/food-template.json')
		* method post
		* status 200
		* def createResponse = response
		
		# Update the food template that was just created
		Given path 'food'
		* def payload = read('classpath:endpoints/food/json/food-template.json')
		* set payload.id = response.id
		* set payload.<fieldName> = <value>
		* request payload
		When method put
		Then status <status>
		* assert (responseStatus != 200) || (response.<fieldName> == <value>)
		
		# Delete food template that was just created
		* path 'food', createResponse.id
		* method delete
		* status 200
		
		Examples:
		| fieldName     | value                                | status |
		| id            | null                                 | 400    |
		| id            | 999999999                            | 404    |
		| calories      | createResponse.calories + 1          | 200    |
		| fat           | createResponse.fat + 1               | 200    |
		| carbs         | createResponse.carbs + 1             | 200    |
		| protein       | createResponse.protein + 1           | 200    |
		| name          | createResponse.carbs + 'abc'         | 200    |
		| brand         | createResponse.brand + 'abc'         | 200    |
		| styleOrFlavor | createResponse.styleOrFlavor + 'abc' | 200    |
		| isTemplate    | null                                 | 400    |
		| isTemplate    | false                                | 400    |
		
	Scenario: Successfully update food template measurements
		# Create a food template
		* path 'food'
		* request read('classpath:endpoints/food/json/food-template.json')
		* method post
		* status 200
		* def createResponse = response
		
		# Update the measurements field (adding another one)
		Given path 'food'
		* def payload = response
		* set payload.measurements[1] = read('classpath:endpoints/food/json/second-measurement.json')
		* request payload
		When method put
		Then status 200
		* match response.measurements == '#[2] schemas.measurement'
		
		# Update the measurements field (remove existing one)
		Given path 'food'
		* def payload = response
		* set payload.measurements = ['#(response.measurements[1])']
		* request payload
		When method put
		Then status 200
		* match response.measurements == '#[1] schemas.measurement'
		
		# Update one of the measurements (update ammount)
		Given path 'food'
		* def payload = response
		* set payload.measurements[0].amount = response.measurements[0].amount + 1
		* request payload
		When method put
		Then status 200
		* match response.measurements[0].amount == payload.measurements[0].amount
		
		# Delete food template that was just created
		* path 'food', createResponse.id
		* method delete
		* status 200
		
	Scenario: Fail to update food (non-template)
		# Try to update a food (non-template) 
		Given path 'food'
		* def payload = read('classpath:endpoints/food/json/food.json')
		* request payload
		When method put
		Then status 400 # not 404 cause it should fail validation before db is queried for existance
	
	# --------------------------------------------
	# DELETE
	# --------------------------------------------
	
	Scenario: Successfully delete food template
		# Create a food tempate
		* path 'food'
		Given request read('classpath:endpoints/food/json/food-template.json')
		When method post
		Then status 200
		* def createdFoodId = response.id
		
		# Get food template that was just created
		* path 'food', createdFoodId
		When method get
		Then status 200
		
		# Delete food template that was just created
		* path 'food', createdFoodId
		When method delete
		Then status 200
		
		# Try to get food template that was just deleted, and fail
		* path 'food', createdFoodId
		When method get
		Then status 404
		
	Scenario: Fail to delete non-existant food template
		* path 'food', 999999999
		When method delete
		Then status 404