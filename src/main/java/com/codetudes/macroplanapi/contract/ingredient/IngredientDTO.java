package com.codetudes.macroplanapi.contract.ingredient;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.dish.DishDTO;
import com.codetudes.macroplanapi.contract.food.FoodDTO;

import lombok.Data;

@Data
public  class IngredientDTO {
	private Long id;

	@NotNull
	private FoodDTO food;

	@NotNull
	private MeasurementDTO measurement;

	@NotNull
	@AssertFalse
	private Boolean isTemplate;
}
