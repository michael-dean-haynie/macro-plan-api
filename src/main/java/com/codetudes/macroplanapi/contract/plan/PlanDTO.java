package com.codetudes.macroplanapi.contract.plan;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.ingredient.IngredientDTO;
import com.codetudes.macroplanapi.contract.plate.PlateDTO;

import lombok.Data;

@Data
public class PlanDTO {
	private Long id;
	
	@NotNull
	private Integer calories;
	
	@NotNull
	private Double fat;
	
	@NotNull
	private Double carbs;
	
	@NotNull
	private Double protein;
	
	@Valid
	private List<IngredientDTO> ingredients;
	
	@Valid
	private List<PlateDTO> plates;
	
	@NotNull
	@AssertFalse
	private Boolean isTemplate;
}
