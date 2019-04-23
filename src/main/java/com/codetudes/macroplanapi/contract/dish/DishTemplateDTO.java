package com.codetudes.macroplanapi.contract.dish;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.ingredient.IngredientTemplateDTO;

import lombok.Data;

@Data
public class DishTemplateDTO {
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotEmpty
	@Valid
	private List<MeasurementDTO> measurements;
	
	@NotEmpty
	@Valid
	private List<IngredientTemplateDTO> ingredients;
	
	@NotNull
	@AssertTrue
	private Boolean isTemplate;
}
