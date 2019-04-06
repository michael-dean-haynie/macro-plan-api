package com.codetudes.macroplanapi.contract;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;

import lombok.Data;

@Data
public class UnitDTO {

	@NotNull
	private Long id;

	@NotNull
	private UnitSystemEnum unitSystem;

	@NotNull
	private UnitTypeEnum unitType;
	
	@NotNull
	private UnitEnum unit;
	
	@NotBlank
	private String properName;
	
	@NotBlank
	private String abbreviation;
	
	@NotNull
	private Double unitTypeRatio;
}
