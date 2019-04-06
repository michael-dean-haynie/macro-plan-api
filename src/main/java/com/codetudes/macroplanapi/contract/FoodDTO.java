package com.codetudes.macroplanapi.contract;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FoodDTO {
	private Long id;
	
	@NotNull
	private Integer calories;
	
	@NotNull
	private Double fat;
	
	@NotNull
	private Double carbs;
	
	@NotNull
	private Double protein;
	
	@NotBlank
	private String name;
	
	private String brand;
	
	private String styleOrFlavor;
	
	@NotEmpty
	@Valid
	private List<MeasurementDTO> measurements;
	
	@NotNull
	private Boolean isTemplate;
}
