package com.codetudes.macroplanapi.db.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="unit_system")
public class UnitSystem {
	
	@Id
	private long id;
	
	private String name;
}
