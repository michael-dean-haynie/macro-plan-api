package com.codetudes.macroplanapi.db.domain.dish;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codetudes.macroplanapi.db.domain.Measurement;
import com.codetudes.macroplanapi.db.domain.ingredient.IngredientTemplate;

import lombok.Data;

@Data
@Entity(name="dish_template")
public class DishTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Measurement> measurements;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IngredientTemplate> ingredients;
	
	private Boolean isTemplate;
}
