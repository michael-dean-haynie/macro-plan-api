package com.codetudes.macroplanapi.contract;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.db.domain.Unit;

import lombok.Data;

@Data
public class MeasurementDTO {
	private Long id;
	
	@NotNull
	@Valid
	private UnitDTO unit;
	
	@NotNull
	private Double value;
}
