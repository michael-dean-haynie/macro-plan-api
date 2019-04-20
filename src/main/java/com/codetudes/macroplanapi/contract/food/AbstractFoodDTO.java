package com.codetudes.macroplanapi.contract.food;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;

import lombok.Data;

@Data
public abstract class AbstractFoodDTO {
	protected Long id;
	
	@NotNull
	protected Integer calories;
	
	@NotNull
	protected Double fat;
	
	@NotNull
	protected Double carbs;
	
	@NotNull
	protected Double protein;
	
	@NotBlank
	protected String name;
	
	protected String brand;
	
	protected String styleOrFlavor;
	
	@NotEmpty
	@Valid
	protected List<MeasurementDTO> measurements;
	
	// javax.validation validators are aggregated through inheritance (not overridden)
	// this abstract class was required to fork the validation between template and non-template foods
	@NotNull
	protected Boolean isTemplate;
}
