package com.codetudes.macroplanapi.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;
import com.codetudes.macroplanapi.service.UnitService;

@RestController
@RequestMapping("unit")
public class UnitController {
	
	@Autowired
	private UnitService unitService;
	
	@GetMapping
	public UnitDTO createUnit() {
		UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUnitSystem(UnitSystemEnum.METRIC);
		unitDTO.setUnitType(UnitTypeEnum.MASS);
		unitDTO.setUnit(UnitEnum.MILLIGRAM);
		unitDTO.setProperName("Milligram");
		unitDTO.setAbbreviation("mg");
		unitDTO.setUnitTypeRatio(0.001d);
		
		return unitService.create(unitDTO);
	}

}
