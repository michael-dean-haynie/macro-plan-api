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
import com.codetudes.macroplanapi.db.domain.ingredient.Ingredient;

import lombok.Data;

@Data
@Entity(name="dish")
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Measurement> measurements;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ingredient> ingredients;
	
	private Boolean isTemplate;
}
