package com.codetudes.macroplanapi.contract;


import com.codetudes.macroplanapi.db.domain.Unit;

import lombok.Data;

@Data
public class MeasurementDTO {
	private Long id;
	
	private UnitDTO unit;
	
	private Double value;
}
