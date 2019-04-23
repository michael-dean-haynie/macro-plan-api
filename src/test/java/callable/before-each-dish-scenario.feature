Feature: Before each scenario create some test data to use while testing dishes endpoints

	Background:
		* url baseUrl

	Scenario: Create food templates and dish template
	
		# --------------------------------------------
		# Create food templates
		# --------------------------------------------
		* path 'food'
		* def payload = read('classpath:endpoints/dish/json/food-template-tuna.json')
		Given request payload
		When method post
		Then status 200
		* def tuna = response
		
		* path 'food'
		* def payload = read('classpath:endpoints/dish/json/food-template-celery.json')
		Given request payload
		When method post
		Then status 200
		* def celery = response
		
		* path 'food'
		* def payload = read('classpath:endpoints/dish/json/food-template-mayo.json')
		Given request payload
		When method post
		Then status 200
		* def mayo = response
		
		* path 'food'
		* def payload = read('classpath:endpoints/dish/json/food-template-relish.json')
		Given request payload
		When method post
		Then status 200
		* def relish = response
		
		* path 'food'
		* def payload = read('classpath:endpoints/dish/json/food-template-bread.json')
		Given request payload
		When method post
		Then status 200
		* def bread = response
		
		# --------------------------------------------
		# Create dish template (tuna sandwich)
		# --------------------------------------------
		
		# Get units
		* path 'unit'
		* method get
		* def units = response
		
		# Convenience function for grabbing particular units by name
    * def getUnitByName = 
    """
    function(units, name){
    	return karate.filter(units, function(u){ return u.unit === name  })[0];
    }
    """
		
		# Put together payload for template dish (tuna sandwich) using existing units and food templates
		* def tunaSWPayload =
		"""
		{
			name: 'Tuna Sandwich',
			measurements: [
				{
					'unit': '#(getUnitByName(units, "GENERIC_ITEM"))',
	        'amount': 1
				}
			],
			ingredients: [
				{
					food: '#(tuna)',
					measurement: {
						'unit': '#(getUnitByName(units, "OUNCE"))',
	        	'amount': 2.57
					},
					isTemplate: true
				},
				{
					food: '#(celery)',
					measurement: {
						'unit': '#(getUnitByName(units, "GENERIC_ITEM"))',
	        	'amount': 0.28
					},
					isTemplate: true
				},
				{
					food: '#(mayo)',
					measurement: {
						'unit': '#(getUnitByName(units, "TABLE_SPOON"))',
	        	'amount': 0.57
					},
					isTemplate: true
				},
				{
					food: '#(relish)',
					measurement: {
						'unit': '#(getUnitByName(units, "TABLE_SPOON"))',
	        	'amount': 0.57
					},
					isTemplate: true
				},
				{
					food: '#(bread)',
					measurement: {
						'unit': '#(getUnitByName(units, "GENERIC_ITEM"))',
	        	'amount': 2
					},
					isTemplate: true
				}
			],
			isTemplate: true
		}
		"""
		
		# Create dish template
		* path 'dish'
		Given request tunaSWPayload
		When method post
		Then status 200
		* def tunaSW = response
		
		