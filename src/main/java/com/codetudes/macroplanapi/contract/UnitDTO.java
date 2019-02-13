package com.codetudes.macroplanapi.contract;

import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;

import lombok.Data;

@Data
public class UnitDTO {

	private Long id;

	private UnitSystemEnum unitSystem;

	private UnitTypeEnum unitType;
	
	private UnitEnum unit;
	
	private String properName;
	
	private String abbreviation;
	
	private Double UnitTypeRatio;
}
