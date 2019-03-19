package com.codetudes.macroplanapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.repository.UnitRepository;
import com.codetudes.macroplanapi.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UnitRepository unitRepository;
	
	// TODO: Match method naming convention with FoodServiceImpl
	@Override
	public UnitDTO create(UnitDTO unitDTO) {
		return mapper.map(unitRepository.save(mapper.map(unitDTO, Unit.class)), UnitDTO.class);
	}
}
