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

import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.contract.food.FoodTemplateDTO;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.domain.food.FoodTemplate;
import com.codetudes.macroplanapi.db.repository.FoodTemplateRepository;
import com.codetudes.macroplanapi.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	FoodTemplateRepository foodTemplateRepository;
	
	@Override
	public FoodTemplateDTO create(FoodTemplateDTO foodTemplateDTO) {
		return mapper.map(foodTemplateRepository.save(mapper.map(foodTemplateDTO, FoodTemplate.class)), FoodTemplateDTO.class);
	}
	
	@Override
	public FoodTemplateDTO get(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<FoodTemplate> result = foodTemplateRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
		return mapper.map(result.get(), FoodTemplateDTO.class);
	}
	
	@Override
	public FoodTemplateDTO update(FoodTemplateDTO foodTemplateDTO) {
		if (foodTemplateDTO.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (!foodTemplateRepository.existsById(foodTemplateDTO.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return mapper.map(foodTemplateRepository.save(mapper.map(foodTemplateDTO, FoodTemplate.class)), FoodTemplateDTO.class);
	}
	
	@Override
	public void delete(Long id) {
		if (!foodTemplateRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		foodTemplateRepository.deleteById(id);
	}
	
	@Override
	public List<FoodTemplateDTO> getViaSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection) {
		// sortField should not be null or empty
		if (sortField.isEmpty() || (sortField == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Sort sortSpec = new Sort(sortDirection, sortField);
		List<FoodTemplate> result = foodTemplateRepository.findAllViaSearchAndSort(searchTerm, sortSpec);
		
		Type dtoListType = new TypeToken<List<FoodTemplateDTO>>(){}.getType();
		return mapper.map(result, dtoListType);
		
	}
}
