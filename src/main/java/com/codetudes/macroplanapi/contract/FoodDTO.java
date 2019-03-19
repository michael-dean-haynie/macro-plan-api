package com.codetudes.macroplanapi.contract;

import java.util.List;

import com.codetudes.macroplanapi.db.domain.Measurement;

import lombok.Data;

@Data
public class FoodDTO {
	private Long id;
	
	private Integer calories;
	
	private Double fat;
	
	private Double carbs;
	
	private Double protein;
	
	private String name;
	
	private String brand;
	
	private String styleOrFlavor;
	
	private List<MeasurementDTO> measurements;
	
	private Boolean isTemplate;
}
