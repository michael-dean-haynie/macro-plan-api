Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}
		
		* set schemas.unit =
		"""
		{
			id: '#number',
			unitSystem: '#string',
			unitType: '#string',
			unit: '#string',
			properName: '##string',
			abbreviation: '##string',
			unitTypeRatio: '#number'
		}
		"""
		
		* set schemas.measurement =
		"""
		{
			id: '#number',
			unit: '#(schemas.unit)',
			amount: '#number'
		}
		"""
		
		* set schemas.food =
		"""
		{
			id: '#number',
			calories: '#number',
			fat: '#number',
			carbs: '#number',
			protein: '#number',
			name: '#string',
			brand: '##string',
			styleOrFlavor: '##string',
			measurements: '#[] schemas.measurement',
			isTemplate: false
		}
		"""
		
		* set schemas.ingredient =
		"""
		{
			id: '#number',
			food: '#(schemas.food)',
			measurement: '#(schemas.measurement)',
			isTemplate: false
		}
		"""
		
		* set schemas.dish =
		"""
		{
			id: '#number',
			name: '#string',
			measurements: '#[] schemas.measurement',
			ingredients: '#[] schemas.ingredient',
			isTemplate: false
		}
		"""
		
		# ------------------------------------------------
		# Templates
		# ------------------------------------------------
		# TODO: figure out if I can use some kind of inheritance like karate's 'copy' keyword
		
		* set schemas.foodTemplate =
		"""
		{
			id: '#number',
			calories: '#number',
			fat: '#number',
			carbs: '#number',
			protein: '#number',
			name: '#string',
			brand: '##string',
			styleOrFlavor: '##string',
			measurements: '#[] schemas.measurement',
			isTemplate: true
		}
		"""
		
		* set schemas.ingredientTemplate =
		"""
		{
			id: '#number',
			food: '#(schemas.foodTemplate)',
			measurement: '#(schemas.measurement)',
			isTemplate: true
		}
		"""
		
		* set schemas.dishTemplate =
		"""
		{
			id: '#number',
			name: '#string',
			measurements: '#[] schemas.measurement',
			ingredients: '#[] schemas.ingredientTemplate',
			isTemplate: true
		}
		"""