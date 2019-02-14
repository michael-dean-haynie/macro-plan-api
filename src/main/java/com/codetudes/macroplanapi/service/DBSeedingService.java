package com.codetudes.macroplanapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.codetudes.macroplanapi.db.domain.Food;
import com.codetudes.macroplanapi.db.domain.Measurement;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;
import com.codetudes.macroplanapi.db.repository.FoodRepository;
import com.codetudes.macroplanapi.db.repository.MeasurementRepository;
import com.codetudes.macroplanapi.db.repository.UnitRepository;

@Service
public class DBSeedingService {
	private static Logger LOG = LoggerFactory.getLogger(DBSeedingService.class);
	
	@Value("${macro-plan.seed-on-startup}")
	private boolean seedOnStartup;
	
	private Map<UnitEnum, Unit> unitMap = new HashMap<>();
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private MeasurementRepository measurementRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
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
		
		createUnit(UnitSystemEnum.GENERIC, UnitTypeEnum.ITEM, UnitEnum.GENERIC_ITEM, "", "", 1d);
		
		// Seed Food Entities (Templates)
		List<Measurement> measurements = new ArrayList<>(); // re-used for every food
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false)});
		createFood(70, 5d, 0d, 6d, "Egg", "Great Value", "Large, White", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 2d, false)});
		createFood(100, 9d, 0d, 6d, "Bacon", "Tyson", "Hickory Smoked", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.CUP), 1d, false), createMeasurement(unitMap.get(UnitEnum.POUND), 1d, false)});
		createFood(130, 5d, 12d, 8d, "Milk", "Great Value", "2% Reduced-Fat", measurements, true, true);
		
		
		
	}
	
	private void createUnit(UnitSystemEnum unitSystem, UnitTypeEnum unitType, UnitEnum unit, String properName, String abbriviation, Double unitTypeRatio) {
		Unit unitEntity = new Unit();
		unitEntity.setUnitSystem(unitSystem);
		unitEntity.setUnitType(unitType);
		unitEntity.setUnit(unit);
		unitEntity.setProperName(properName);
		unitEntity.setAbbreviation(abbriviation);
		unitEntity.setUnitTypeRatio(unitTypeRatio);
		unitMap.put(unit, unitRepository.save(unitEntity));
	}
	
	private Measurement createMeasurement(Unit unit, Double value, Boolean saveImmediately) {
		Measurement measurement = new Measurement();
		measurement.setUnit(unit);
		measurement.setValue(value);
		return saveImmediately ? measurementRepository.save(measurement) : measurement;
	}
	
	private Food createFood(Integer calories, Double fat, Double carbs, Double protein, String name, String brand, String styleOrFlavor, List<Measurement> measurements, Boolean isTemplate, Boolean saveImmediately) {
		Food food = new Food();
		food.setCalories(calories);
		food.setFat(fat);
		food.setCarbs(carbs);
		food.setProtein(protein);
		food.setName(name);
		food.setBrand(brand);
		food.setStyleOrFlavor(styleOrFlavor);
		food.setMeasurements(measurements);
		food.setIsTemplate(isTemplate);
		return saveImmediately ? foodRepository.save(food) : food;
	}
}
