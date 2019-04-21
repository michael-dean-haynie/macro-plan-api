package com.codetudes.macroplanapi.db.domain.food;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.codetudes.macroplanapi.db.domain.Measurement;

import lombok.Data;

@Data
@Entity(name="food_template")
public class FoodTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	private Integer calories;
	
	private Double fat;
	
	private Double carbs;
	
	private Double protein;
	
	private String name;
	
	private String brand;
	
	private String styleOrFlavor;
	
	@OneToMany(cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Measurement> measurements;
	
	private Boolean isTemplate;
	
	/**
	 * Calculated fields (not returned, just used for sorting)
	 */
	@Formula("(fat * 9) / ((fat * 9) + (carbs * 4) + (protein * 4)) * 100")
	private Double fatPercentage;
	
	@Formula("(carbs * 4) / ((fat * 9) + (carbs * 4) + (protein * 4)) * 100")
	private Double carbsPercentage;
	
	@Formula("(protein * 4) / ((fat * 9) + (carbs * 4) + (protein * 4)) * 100")
	private Double proteinPercentage;
}
