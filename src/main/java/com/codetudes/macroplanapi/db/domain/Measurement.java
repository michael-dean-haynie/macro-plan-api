package com.codetudes.macroplanapi.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name="measurement")
public class Measurement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	// TODO: https://stackoverflow.com/questions/45491551/refresh-and-fetch-an-entity-after-save-jpa-spring-data-hibernate?rq=1
	@ManyToOne // <-- Do not let other entities save/update nested units via cascade
	private Unit unit;
	
	private Double value;
}
