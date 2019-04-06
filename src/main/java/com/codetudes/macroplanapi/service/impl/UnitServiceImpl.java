package com.codetudes.macroplanapi.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
	
	@Override
	public UnitDTO createUnit(UnitDTO unitDTO) {
		return mapper.map(unitRepository.save(mapper.map(unitDTO, Unit.class)), UnitDTO.class);
	}
	
	public List<UnitDTO>getUnits() {
		Type dtoListType = new TypeToken<List<UnitDTO>>(){}.getType();
		return mapper.map(unitRepository.findAll(), dtoListType);
	}
}
