package com.codetudes.macroplanapi.contract.ingredient;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;
import com.codetudes.macroplanapi.contract.food.FoodTemplateDTO;

import lombok.Data;

@Data
public  class IngredientTemplateDTO {
	private Long id;

	@NotNull
	private FoodTemplateDTO food;

	@NotNull
	private MeasurementDTO measurement;
	
	@NotNull
	@AssertTrue
	private Boolean isTemplate;
}
