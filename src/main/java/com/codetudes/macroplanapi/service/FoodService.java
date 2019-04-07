package com.codetudes.macroplanapi.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.codetudes.macroplanapi.contract.FoodDTO;

public interface FoodService {
	
	FoodDTO createFood(FoodDTO foodDTO);
	
	FoodDTO getFood(Long id);
	
	FoodDTO updateFood(FoodDTO foodDTO);
	
	void deleteFood(Long id);
	
	List<FoodDTO> getAllTemplatesWithSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection);
}
