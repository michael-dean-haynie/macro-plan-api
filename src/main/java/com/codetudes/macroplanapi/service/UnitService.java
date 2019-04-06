package com.codetudes.macroplanapi.service;

import java.util.List;

import com.codetudes.macroplanapi.contract.UnitDTO;

public interface UnitService {

	UnitDTO createUnit(UnitDTO unitDTO);
	
	List<UnitDTO> getUnits();

}