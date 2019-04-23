package com.codetudes.macroplanapi.contract.dish;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.ingredient.IngredientDTO;

public class DishDTO {
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotEmpty
	@Valid
	private List<MeasurementDTO> measurements;
	
	@NotEmpty
	@Valid
	private List<IngredientDTO> ingredients;
	
	@NotNull
	@AssertFalse
	private Boolean isTemplate;
}
