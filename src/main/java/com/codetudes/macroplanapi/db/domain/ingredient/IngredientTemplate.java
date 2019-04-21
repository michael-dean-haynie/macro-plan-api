package com.codetudes.macroplanapi.db.domain.ingredient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.codetudes.macroplanapi.db.domain.Measurement;
import com.codetudes.macroplanapi.db.domain.dish.DishTemplate;
import com.codetudes.macroplanapi.db.domain.food.FoodTemplate;

import lombok.Data;

@Data
@Entity(name="ingredient_template")
public class IngredientTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	@OneToOne
	private FoodTemplate food;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Measurement measurement;
	
	private Boolean isTemplate;
}
