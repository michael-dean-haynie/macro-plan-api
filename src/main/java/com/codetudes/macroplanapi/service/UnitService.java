package com.codetudes.macroplanapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.domain.Unit;
import com.codetudes.macroplanapi.db.repository.UnitRepository;

@Service
public class UnitService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UnitRepository unitRepository;
	
	public UnitDTO create(UnitDTO unitDTO) {
		return mapper.map(unitRepository.save(mapper.map(unitDTO, Unit.class)), UnitDTO.class);
	}
}
