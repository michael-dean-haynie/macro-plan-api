package com.codetudes.macroplanapi.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;

public interface DishService {
	
	DishTemplateDTO create(DishTemplateDTO dishTemplateDTO);
	
	DishTemplateDTO get(Long id);
	
	DishTemplateDTO update(DishTemplateDTO dishTemplateDTO);
	
	void delete(Long id);
	
	List<DishTemplateDTO> getViaSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection);
}
