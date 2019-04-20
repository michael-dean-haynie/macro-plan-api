package com.codetudes.macroplanapi.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.codetudes.macroplanapi.contract.FoodTemplateDTO;

public interface FoodService {
	
	FoodTemplateDTO create(FoodTemplateDTO foodTemplateDTO);
	
	FoodTemplateDTO get(Long id);
	
	FoodTemplateDTO update(FoodTemplateDTO foodTemplateDTO);
	
	void delete(Long id);
	
	List<FoodTemplateDTO> getViaSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection);
}
