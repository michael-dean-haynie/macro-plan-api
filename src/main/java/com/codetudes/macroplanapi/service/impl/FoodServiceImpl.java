package com.codetudes.macroplanapi.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codetudes.macroplanapi.contract.FoodDTO;
import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.domain.FoodTemplate;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.repository.FoodTemplateRepository;
import com.codetudes.macroplanapi.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	FoodTemplateRepository foodTemplateRepository;
	
	@Override
	public FoodDTO createFood(FoodDTO foodDTO) {
		return mapper.map(foodTemplateRepository.save(mapper.map(foodDTO, FoodTemplate.class)), FoodDTO.class);
	}
	
	@Override
	public FoodDTO getFood(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<FoodTemplate> result = foodTemplateRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return mapper.map(result.get(), FoodDTO.class);
	}
	
	@Override
	public FoodDTO updateFood(FoodDTO foodDTO) {
		if (foodDTO.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (!foodTemplateRepository.existsById(foodDTO.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		// Should not be able to update non-template foods
		Optional<FoodTemplate> result = foodTemplateRepository.findById(foodDTO.getId());
		if (result.isPresent() && (!result.get().getIsTemplate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		// Should not be able to change isTemplate field
		Optional<FoodTemplate> result2 = foodTemplateRepository.findById(foodDTO.getId());
		if (result2.isPresent() && (result2.get().getIsTemplate() != foodDTO.getIsTemplate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		return mapper.map(foodTemplateRepository.save(mapper.map(foodDTO, FoodTemplate.class)), FoodDTO.class);
	}
	
	@Override
	public void deleteFood(Long id) {
		if (!foodTemplateRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		foodTemplateRepository.deleteById(id);
	}
	
	@Override
	public List<FoodDTO> getAllTemplatesWithSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection) {
		// sortField should not be null or empty
		if (sortField.isEmpty() || (sortField == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Sort sortSpec = new Sort(sortDirection, sortField);
		List<FoodTemplate> result = foodTemplateRepository.findAllTemplatesWithSearchAndSort(searchTerm, sortSpec);
		
		Type dtoListType = new TypeToken<List<FoodDTO>>(){}.getType();
		return mapper.map(result, dtoListType);
		
	}
}
