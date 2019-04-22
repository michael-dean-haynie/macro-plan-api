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

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;
import com.codetudes.macroplanapi.db.domain.dish.DishTemplate;
import com.codetudes.macroplanapi.db.repository.DishTemplateRepository;
import com.codetudes.macroplanapi.service.DishService;

@Service
public class DishServiceImpl implements DishService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	DishTemplateRepository dishTemplateRepository;
	
	@Override
	public DishTemplateDTO create(DishTemplateDTO dishTemplateDTO) {
		return mapper.map(dishTemplateRepository.save(mapper.map(dishTemplateDTO, DishTemplate.class)), DishTemplateDTO.class);
	}
	
	@Override
	public DishTemplateDTO get(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<DishTemplate> result = dishTemplateRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
		return mapper.map(result.get(), DishTemplateDTO.class);
	}
	
	@Override
	public DishTemplateDTO update(DishTemplateDTO dishTemplateDTO) {
		if (dishTemplateDTO.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (!dishTemplateRepository.existsById(dishTemplateDTO.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return mapper.map(dishTemplateRepository.save(mapper.map(dishTemplateDTO, DishTemplate.class)), DishTemplateDTO.class);
	}
	
	@Override
	public void delete(Long id) {
		if (!dishTemplateRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		dishTemplateRepository.deleteById(id);
	}
	
	@Override
	public List<DishTemplateDTO> getViaSearchAndSort(String searchTerm, String sortField, Sort.Direction sortDirection) {
		// sortField should not be null or empty
		if (sortField.isEmpty() || (sortField == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Sort sortSpec = new Sort(sortDirection, sortField);
		List<DishTemplate> result = dishTemplateRepository.findAllViaSearchAndSort(searchTerm, sortSpec);
		
		Type dtoListType = new TypeToken<List<DishTemplateDTO>>(){}.getType();
		return mapper.map(result, dtoListType);
		
	}
}
