package com.codetudes.macroplanapi.contract.food;

import javax.validation.constraints.AssertTrue;


public class FoodTemplateDTO extends AbstractFoodDTO{

	@Override
	@AssertTrue // <-- Whole point of this class
	public Boolean getIsTemplate() {
		return isTemplate;
	}
}
