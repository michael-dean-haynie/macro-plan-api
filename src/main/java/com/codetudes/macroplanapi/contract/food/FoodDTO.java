package com.codetudes.macroplanapi.contract.food;

import javax.validation.constraints.AssertFalse;

public class FoodDTO extends AbstractFoodDTO {
	@Override
	@AssertFalse // <-- Whole point of this class
	public Boolean getIsTemplate() {
		return isTemplate;
	}
}
