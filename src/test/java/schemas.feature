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
			properName: '#string',
			abbreviation: '#string',
			unitTypeRatio: '#number'
		}
		"""
		
		* set schemas.measurement =
		"""
		{
			id: '#number',
			unit: '#(schemas.unit)',
			value: '#number'
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
			brand: '#string',
			styleOrFlavor: '#string',
			measurements: '#[] schemas.measurement',
			isTemplate: '#boolean'
		}
		"""