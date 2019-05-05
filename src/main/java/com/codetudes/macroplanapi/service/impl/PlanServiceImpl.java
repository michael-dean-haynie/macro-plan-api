package com.codetudes.macroplanapi.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codetudes.macroplanapi.contract.plan.PlanTemplateDTO;
import com.codetudes.macroplanapi.db.domain.plan.PlanTemplate;
import com.codetudes.macroplanapi.db.repository.PlanTemplateRepository;
import com.codetudes.macroplanapi.service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	PlanTemplateRepository planTemplateRepository;
	
	@Override
	public PlanTemplateDTO create(PlanTemplateDTO planTemplateDTO) {
		return mapper.map(planTemplateRepository.save(mapper.map(planTemplateDTO, PlanTemplate.class)), PlanTemplateDTO.class);
	}
	
	@Override
	public PlanTemplateDTO get(Long id) {
		// TODO look into .ifPresent on Optional
		Optional<PlanTemplate> result = planTemplateRepository.findById(id);
		if (!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
			
		return mapper.map(result.get(), PlanTemplateDTO.class);
	}
	
	@Override
	public PlanTemplateDTO update(PlanTemplateDTO planTemplateDTO) {
		if (planTemplateDTO.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (!planTemplateRepository.existsById(planTemplateDTO.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return mapper.map(planTemplateRepository.save(mapper.map(planTemplateDTO, PlanTemplate.class)), PlanTemplateDTO.class);
	}
	
	@Override
	public void delete(Long id) {
		if (!planTemplateRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		planTemplateRepository.deleteById(id);
	}
}
