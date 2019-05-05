package com.codetudes.macroplanapi.contract.plan;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.ingredient.IngredientTemplateDTO;
import com.codetudes.macroplanapi.contract.plate.PlateTemplateDTO;

import lombok.Data;

@Data
public class PlanTemplateDTO {
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
	private List<IngredientTemplateDTO> ingredients;
	
	@Valid
	private List<PlateTemplateDTO> plates;
	
	@NotNull
	@AssertTrue
	private Boolean isTemplate;
}
