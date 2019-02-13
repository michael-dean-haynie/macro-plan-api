package com.codetudes.macroplanapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;
import com.codetudes.macroplanapi.db.repository.UnitRepository;

@Service
public class DBSeedingService {
	private static Logger LOG = LoggerFactory.getLogger(DBSeedingService.class);
	
	@Value("${macro-plan.seed-on-startup}")
	private boolean seedOnStartup;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@EventListener()
	public void seedDatabase(ContextRefreshedEvent event) {
		LOG.info("seed-on-startup functionality enabled: " + seedOnStartup);
		if (!seedOnStartup) { return; }
		
		// Seed Unit Entities
		createUnit(UnitSystemEnum.METRIC, UnitTypeEnum.MASS, UnitEnum.MILLIGRAM, "Milligram", "mg", 0.001d);
		createUnit(UnitSystemEnum.METRIC, UnitTypeEnum.MASS, UnitEnum.GRAM, "Gram", "g", 1d);
		createUnit(UnitSystemEnum.METRIC, UnitTypeEnum.MASS, UnitEnum.KILOGRAM, "Kilogram", "kg", 1000d);
		
		createUnit(UnitSystemEnum.METRIC, UnitTypeEnum.VOLUME, UnitEnum.MILLILITER, "Milliliter", "mL", 0.033814d);
		createUnit(UnitSystemEnum.METRIC, UnitTypeEnum.VOLUME, UnitEnum.LITER, "Liter", "L", 33.814d);
		
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.MASS, UnitEnum.OUNCE, "Ounce", "oz", 28.3495d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.MASS, UnitEnum.POUND, "Pound", "lb", 453.592d);
		
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.TEA_SPOON, "Teaspoon", "tsp", 0.166667d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.TABLE_SPOON, "Tablespoon", "tbsp", 0.5d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.FLUID_OUNCE, "Fluid Ounce", "fl oz", 1d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.CUP, "Cup", "c", 8d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.PINT, "Pint", "pt", 16d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.QUART, "Quart", "qt", 32d);
		createUnit(UnitSystemEnum.IMPERIAL, UnitTypeEnum.VOLUME, UnitEnum.GALLON, "Gallon", "gal", 128d);
	}
	
	private Unit createUnit(UnitSystemEnum unitSystem, UnitTypeEnum unitType, UnitEnum unit, String properName, String abbriviation, Double unitTypeRatio) {
		Unit unitEntity = new Unit();
		unitEntity.setUnitSystem(unitSystem);
		unitEntity.setUnitType(unitType);
		unitEntity.setUnit(unit);
		unitEntity.setProperName(properName);
		unitEntity.setAbbreviation(abbriviation);
		unitEntity.setUnitTypeRatio(unitTypeRatio);
		return unitRepository.save(unitEntity);
	}
}
