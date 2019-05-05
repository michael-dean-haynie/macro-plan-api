package com.codetudes.macroplanapi.contract.plate;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.contract.MeasurementDTO;
import com.codetudes.macroplanapi.contract.dish.DishDTO;

import lombok.Data;

@Data
public class PlateDTO {
	private Long id;
	
	@NotNull
	private DishDTO dish;
	
	@NotNull
	private MeasurementDTO measurement;
	
	@NotNull
	@AssertFalse
	private Boolean isTemplate;
}
