package com.codetudes.macroplanapi.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.service.UnitService;

@RestController
@RequestMapping("unit")
public class UnitController {
	
	@Autowired
	private UnitService unitService;
	
	@GetMapping
	public List<UnitDTO> getUnits() {
		return unitService.getUnits();
	}
}
