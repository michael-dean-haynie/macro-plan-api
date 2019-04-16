Feature: Test the food endpoints

	Background:
		* url baseUrl

	Scenario: Successfully create a food using existing unit. Validate schema and values.
		#	Get units	 
		* path 'unit'
		* method get
		* def units = response
		
		# Create a food
		* path 'food'
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
		* set payload.measurements = [{unit: '#(units[0])', value: 42}]
		Given request payload
		When method post
		Then status 200
		
		# Validate schema
		* match response == schemas.food
		
		# Validate Values
		* assert response.calories == payload.calories
		* assert response.fat == payload.fat
		* assert response.carbs == payload.carbs
		* assert response.protein == payload.protein
		* assert response.name == payload.name
		* assert response.brand == payload.brand
		* assert response.styleOrFlavor == payload.styleOrFlavor
		* assert response.measurements[0].unit.id == payload.measurements[0].unit.id
		* assert response.measurements[0].value == payload.measurements[0].value
		* assert response.isTemplate == payload.isTemplate
		
		# Delete food that was just created
		* path 'food', response.id
		* method delete
		* status 200
		
	Scenario Outline: Fail to create food due invalid field (<fieldName> = <value>)
		* def payload = read('classpath:endpoints/food/json/happy-path-food.json')
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
		
	Scenario: Successfully get a food
		# First, create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/happy-path-food.json')
		* method post
		* status 200
	
		# Get same food that was just created
		Given path 'food', response.id
		When method get
		Then status 200
		And match response == schemas.food
		
		# Delete food that was just created
		* path 'food', response.id
		* method delete
		* status 200
		
	Scenario: Fail to get a food that does not exist
		Given path 'food', 999999999
		When method get
		Then status 404
		
	Scenario Outline: Fail or succeed to update a template food (set payload.<fieldName> = <value> and expect <status>)
		# Create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/template-food.json')
		* method post
		* status 200
		* def createResponse = response
		
		# Update the food that was just created
		Given path 'food'
		* def payload = read('classpath:endpoints/food/json/template-food.json')
		* set payload.id = response.id
		* set payload.<fieldName> = <value>
		* request payload
		When method put
		Then status <status>
		* assert (responseStatus != 200) || (response.<fieldName> == <value>)
		
		# Delete food that was just created
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
		
	Scenario: Fail to update isTemplate field on template food
		# Create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/template-food.json')
		* method post
		* status 200
		* def createResponse = response
		
		# Try to update the isTemplate field on the food that was just created
		Given path 'food'
		* def payload = read('classpath:endpoints/food/json/template-food.json')
		* set payload.id = response.id
		* set payload.isTemplate = false
		* request payload
		When method put
		Then status 400
		
		# Delete food that was just created
		* path 'food', createResponse.id
		* method delete
		* status 200
		
	Scenario: Successfully update template food measurements
		# Create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/template-food.json')
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
		
		# Delete food that was just created
		* path 'food', createResponse.id
		* method delete
		* status 200
		
	Scenario: Fail to update non-template food
		# Create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/non-template-food.json')
		* method post
		* status 200
		* def createResponse = response
		
		# Try to update the non-template food that was just created
		Given path 'food'
		* def payload = response
		* set payload.calories = response.calories + 1
		* request payload
		When method put
		Then status 400
		
		# Delete food that was just created
		* path 'food', createResponse.id
		* method delete
		* status 200
	
	Scenario: Successfully delete food
		# Create a food
		* path 'food'
		Given request read('classpath:endpoints/food/json/happy-path-food.json')
		When method post
		Then status 200
		* def createdFoodId = response.id
		
		# Get food that was just created
		* path 'food', createdFoodId
		When method get
		Then status 200
		
		# Delete food that was just created
		* path 'food', createdFoodId
		When method delete
		Then status 200
		
		# Try to get food that was just deleted
		* path 'food', createdFoodId
		When method get
		Then status 404
		
	Scenario: Fail to delete non-existant food
		* path 'food', 999999999
		When method delete
		Then status 404