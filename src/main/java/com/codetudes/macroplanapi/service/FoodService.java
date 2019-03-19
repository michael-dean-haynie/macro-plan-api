package com.codetudes.macroplanapi.service;

import com.codetudes.macroplanapi.contract.FoodDTO;

public interface FoodService {
	
	FoodDTO createFood(FoodDTO foodDTO);
	
	FoodDTO getFood(Long id);
}
