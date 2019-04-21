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
import com.codetudes.macroplanapi.db.domain.dish.Dish;
import com.codetudes.macroplanapi.db.domain.food.Food;

import lombok.Data;

@Data
@Entity(name="ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Food food;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Measurement measurement;
	
	private Boolean isTemplate;
}
