package com.codetudes.macroplanapi.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codetudes.macroplanapi.contract.FoodDTO;
import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.domain.Food;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.repository.FoodRepository;
import com.codetudes.macroplanapi.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	FoodRepository foodRepository;
	
	public FoodDTO createFood(FoodDTO foodDTO) {
		return mapper.map(foodRepository.save(mapper.map(foodDTO, Food.class)), FoodDTO.class);
	}
	
	public FoodDTO getFood(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<Food> result = foodRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return mapper.map(result.get(), FoodDTO.class);
	}
	
	public FoodDTO updateFood(FoodDTO foodDTO) {
		if (foodDTO.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (!foodRepository.existsById(foodDTO.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		// Should not be able to update non-template foods
		Optional<Food> result = foodRepository.findById(foodDTO.getId());
		if (result.isPresent() && (!result.get().getIsTemplate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		// Should not be able to change isTemplate field
		Optional<Food> result2 = foodRepository.findById(foodDTO.getId());
		if (result2.isPresent() && (result2.get().getIsTemplate() != foodDTO.getIsTemplate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		return mapper.map(foodRepository.save(mapper.map(foodDTO, Food.class)), FoodDTO.class);
	}
	
	public void deleteFood(Long id) {
		if (!foodRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		foodRepository.deleteById(id);
	}
}
