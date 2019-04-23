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
	
	# --------------------------------------------
	# CREATE
	# --------------------------------------------
	
	Scenario: Successfully create a dish template 
		# Actual creation happens in 'before-each-dish-scenario.feature'. Cause other scenarios need it too.
		# Here we're just asserting stuff.
		* match prepData.tunaSW == schemas.dishTemplate
		# TODO: find neat way to assert each field value was set properly. Not just the shape of the obj.
		
	Scenario Outline: Fail to create a dish template due to invalid field (<fieldName> = <value>)
		* path 'dish'
		* def payload = prepData.tunaSWPayload
		* set payload.<fieldName> = <value>
		* request payload
		When method post
		Then status 400
		
		Examples:
		| fieldName                          | value |
		| name                               | null  |
		| name                               | ''    |
		| name                               | ' '   |
		| measurements                       | null  |
		| measurements                       | []    |
		# more extensive measurement.<field> value testing in food-template.feature
		| ingredients                        | null  |
		| ingredients                        | []    |
		# more extensive food.<field> value testing in food-template.feature
		| ingredients[0].isTemplate          | null  |
		| ingredients[0].isTemplate          | false |
		| isTemplate                         | null  |
		| isTemplate                         | false |
		
	
		
	# --------------------------------------------
	# READ
	# --------------------------------------------
		
	Scenario: Successfully read a dish template
		* path 'dish', prepData.tunaSW.id
		When method get
		Then status 200
		* match response == schemas.dishTemplate
		
	Scenario: Fail to read a dish template that does not exist (probably)
		* path 'dish', 9999999999
		When method get
		Then status 404
		
	# --------------------------------------------
	# UPDATE
	# --------------------------------------------
	
	Scenario Outline: Fail or succeed to update a dish template (set payload.<fieldName> = <value> and expect <status>)

		# Update the dish template that was created in before-each-dish-scenario.feature
		Given path 'dish'
		* copy payload = prepData.tunaSW
		* set payload.<fieldName> = <value>
		* request payload
		When method put
		Then status <status>
		* print responseStatus
		* print response
		* print response.<fieldName>
		* print prepData.tunaSW.name + 'a'
		* assert (responseStatus != 200) || (response.<fieldName> == <value>)
		
		Examples:
		| fieldName     | value                                | status |
		| id            | null                                 | 400    |
		| id            | 999999999                            | 404    |
		| name          | prepData.tunaSW.name + 'a'           | 200    |
		| isTemplate    | null                                 | 400    |
		| isTemplate    | false                                | 400    |
		
	Scenario: Successfully update dish template measurements
		# Update the measurements field (adding another one)
		Given path 'dish'
		* copy payload = prepData.tunaSW
		* set payload.measurements[1] = read('classpath:endpoints/dish/json/second-measurement.json')
		* request payload
		When method put
		Then status 200
		* match response.measurements == '#[2] schemas.measurement'
		
		# Update the measurements field (remove existing one)
		Given path 'dish'
		* def payload = response
		* set payload.measurements = ['#(response.measurements[1])']
		* request payload
		When method put
		Then status 200
		* match response.measurements == '#[1] schemas.measurement'
		
		# Update one of the measurements (update ammount)
		Given path 'dish'
		* def payload = response
		* set payload.measurements[0].amount = response.measurements[0].amount + 1
		* request payload
		When method put
		Then status 200
		* match response.measurements[0].amount == payload.measurements[0].amount
		
	Scenario: Fail to update value on unit on measurement (units are only referenced, never mutated)
		# Try to update value on unit
		Given path 'dish'
		* def valueBefore = prepData.tunaSW.measurements[0].unit.properName
		* def valueAfter = 'asdfaf;ajjsgvjsoifjqe0918ufdpojh.LKVBAP0921'
		* copy payload = prepData.tunaSW
		* set payload.measurements[0].unit.properName = valueAfter
		* request payload
		When method put
		Then status 200
		
		# Right now, units in api responses don't reflect the database state, but the dto. It's cause after a save happens
		# the entity isn't being refreshed cause measurements aren't cascading any actions to units (so they can't be updated).
		# Work around is just to use the GET api which does reflect db state.
		* path 'dish', response.id
		When method get
		Then status 200
		
		# assert that value on unit did not actually change
		* match response.measurements[0].unit.properName == valueBefore
		
	Scenario: Successfully update dish template's ingredient templates
		* copy payload = prepData.tunaSW
		* def fifthIngredient = payload.ingredients[4];
		
		# Update the ingredients field (remove existing one)
		Given path 'dish'
		* remove payload $.ingredients[4]
		* request payload
		When method put
		Then status 200
		* match response.ingredients == '#[4] schemas.ingredientTemplate'
		
		# Update the ingredients field (adding another one)
		Given path 'dish'
		* def payload = response
		* set payload.ingredients[4] = fifthIngredient
		* request payload
		When method put
		Then status 200
		* match response.ingredients == '#[5] schemas.ingredientTemplate'
		
		# Update one of the ingredients (update food)
		Given path 'dish'
		* def payload = response
		* set payload.ingredients[0].food = payload.ingredients[1].food
		* request payload
		When method put
		Then status 200
		* match response.ingredients[0].food == payload.ingredients[0].food
		
	
	# --------------------------------------------
	# DELETE
	# --------------------------------------------
	
	Scenario: Successfully delete dish template
		# Create a dish template
		* path 'dish'
		* copy payload = prepData.tunaSWPayload
		* request payload
		When method post
		Then status 200 
		* def createdDishId = response.id
		
		# Get the dish template that was just created
		* path 'dish', createdDishId
		When method get
		Then status 200
		
		# Delte dish template that was just created
		* path 'dish', createdDishId
		When method delete
		Then status 200
		
		# Try to get dish template that was just deleted, and fail
		* path 'dish', createdDishId
		When method get
		Then status 404
		
		
		
		