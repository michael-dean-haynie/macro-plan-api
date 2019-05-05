package com.codetudes.macroplanapi.contract.plate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;

import lombok.Data;

@Data
public class PlateTemplateDTO {
	private Long id;
	
	@NotNull
	private DishTemplateDTO dish;
	
	@NotNull
	private MeasurementDTO measurement;
	
	@NotNull
	@AssertTrue
	private Boolean isTemplate;
}
