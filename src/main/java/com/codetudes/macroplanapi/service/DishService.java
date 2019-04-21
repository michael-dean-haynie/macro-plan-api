package com.codetudes.macroplanapi.service;

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;

public interface DishService {
	
	DishTemplateDTO get(Long id);
}
