package com.codetudes.macroplanapi.db.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity(name="food")
public class Food {
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
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Measurement> measurements;
	
	private Boolean isTemplate;
}
