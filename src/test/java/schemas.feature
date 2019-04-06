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