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
		
		createUnit(UnitSystemEnum.GENERIC, UnitTypeEnum.ITEM, UnitEnum.GENERIC_ITEM, "Item", "item(s)", 1d);
		
		// Seed Food Entities (Templates)
		List<Measurement> measurements = new ArrayList<>(); // re-used for every food
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false)});
		createFood(70, 5d, 0d, 6d, "Eggs", "Great Value", "Large, White", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 2d, false)});
		createFood(100, 9d, 0d, 6d, "Bacon", "Tyson", "Hickory Smoked", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.CUP), 1d, false)});
		createFood(130, 5d, 12d, 8d, "Milk", "Great Value", "2% Reduced-Fat", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 17d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 28d, false)});
		createFood(110, 1d, 23d, 2d, "Pretzels", "Rold Gold", "Tiny Twists, Original", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 27d, false)});
		createFood(90, 2d, 11d, 10d, "Protein Bars", "Protein One", "Strawberries & Cream", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 26d, false)});
		createFood(70, 1.5d, 12d, 4d, "Sandwich Bread", "Nature's Own", "100% Whole Grain", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 90d, false)});
		createFood(260, 12d, 36d, 2d, "Drumsticks", "Nestle", "Crunch Dipped, Vanilla", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 95d, false)});
		createFood(280, 12d, 41d, 2d, "Drumsticks", "Nestle", "Crunch Dipped, Vanilla Caramel", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 94d, false)});
		createFood(280, 13d, 39d, 2d, "Drumsticks", "Nestle", "Crunch Dipped, Vanilla Fudge", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.TABLE_SPOON), 1d, false)});
		createFood(15, 0d, 3d, 0d, "Sweet Relish", "Heinz", null, measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.TABLE_SPOON), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 13d, false)});
		createFood(90, 10d, 0d, 0d, "Mayonnaise", "Kraft", null, measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.CUP), .33d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 85d, false)});
		createFood(100, 3.5d, 0d, 16d, "Tuna", "StarKist", "Chunk Light", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 2d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 85d, false)});
		createFood(15, 0d, 4d, 1d, "Celery Sticks", "Dandy", null, measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GENERIC_ITEM), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 172d, false)});
		createFood(98, 0.2d, 24d, 0.4d, "Apples", null, "Gala", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.GRAM), 112d, false)});
		createFood(140, 4d, 0d, 25d, "Chicken Breasts", "Great Value", "Boneless, Skinnless, with rib meat", measurements, true, true);
		
		measurements = Arrays.asList(new Measurement[] {createMeasurement(unitMap.get(UnitEnum.CUP), 1d, false), createMeasurement(unitMap.get(UnitEnum.GRAM), 158d, false)});
		createFood(205, .4d, 44.5d, 4.2d, "Rice", "Great Value", "White, long-grain, cooked", measurements, true, true);
		
		
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
