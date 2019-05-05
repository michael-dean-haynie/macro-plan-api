package com.codetudes.macroplanapi.db.domain.plan;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codetudes.macroplanapi.db.domain.ingredient.Ingredient;
import com.codetudes.macroplanapi.db.domain.plate.Plate;

import lombok.Data;

@Data
@Entity(name="plan")
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	private Integer calories;
	
	private Double fat;
	
	private Double carbs;
	
	private Double protein;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ingredient> ingredients;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Plate> plates;
	
	private Boolean isTemplate;
}
