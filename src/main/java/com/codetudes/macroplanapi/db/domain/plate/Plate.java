package com.codetudes.macroplanapi.db.domain.plate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.codetudes.macroplanapi.db.domain.Measurement;
import com.codetudes.macroplanapi.db.domain.dish.Dish;

import lombok.Data;

@Data
@Entity(name="plate")
public class Plate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	@OneToOne
	private Dish dish;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Measurement measurement;
	
	private Boolean isTemplate;
}
