package com.codetudes.macroplanapi.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;

import lombok.Data;

@Data
@Entity(name="unit")
public class Unit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private UnitSystemEnum unitSystem;
	
	@Enumerated(EnumType.STRING)
	private UnitTypeEnum unitType;
	
	@Enumerated(EnumType.STRING)
	private UnitEnum unit;
	
	private String properName;
	
	private String abbreviation;
	
	private Double UnitTypeRatio;
	
}
