package com.codetudes.macroplanapi.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;
import com.codetudes.macroplanapi.db.domain.dish.DishTemplate;
import com.codetudes.macroplanapi.db.repository.DishTemplateRepository;
import com.codetudes.macroplanapi.service.DishService;

@Service
public class DishServiceImpl implements DishService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	DishTemplateRepository dishTemplateRepository;
	
	@Override
	public DishTemplateDTO get(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<DishTemplate> result = dishTemplateRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
		return mapper.map(result.get(), DishTemplateDTO.class);
	}
}
