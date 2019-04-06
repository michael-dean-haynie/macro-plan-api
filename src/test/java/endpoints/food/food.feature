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
		* def payload = read('classpath:endpoints/food/json/create-food.json')
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
		
	Scenario Outline: Fail to create food due invalid field (<fieldName> = <value>)
		* def payload = read('classpath:endpoints/food/json/create-food.json')
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
		| measurements[0].unit.properName    | null  |
		| measurements[0].unit.properName    | ''    |
		| measurements[0].unit.properName    | ' '   |
		| measurements[0].unit.abbreviation  | null  |
		| measurements[0].unit.abbreviation  | ''    |
		| measurements[0].unit.abbreviation  | ' '   |
		| measurements[0].unit.unitTypeRatio | null  |
		| measurements                       | null  |
		| measurements                       | []    |
		| isTemplate                         | null  |
		
	Scenario: Successfully get a food.
		# First, create a food
		* path 'food'
		* request read('classpath:endpoints/food/json/create-food.json')
		* method post
		* status 200
	
		# Get save food that was just created
		Given path 'food', response.id
		When method get
		Then status 200
		And match response == schemas.food