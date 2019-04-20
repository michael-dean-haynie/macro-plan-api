package com.codetudes.macroplanapi.contract;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

public class FoodDTO extends FoodTemplateDTO {
	// TODO: Figure out how to overide this validation. Instance variables are not accessible to polymorphism, but could do with getters/setters? Will require lombok config.
	@AssertFalse
	@NotNull
	private Boolean isTemplate;
}
