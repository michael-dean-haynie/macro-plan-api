package com.codetudes.macroplanapi.service;

import java.util.List;

import com.codetudes.macroplanapi.contract.plan.PlanTemplateDTO;

public interface PlanService {
	PlanTemplateDTO create(PlanTemplateDTO planTemplateDTO);
	
	PlanTemplateDTO get(Long id);
	
	PlanTemplateDTO update(PlanTemplateDTO planTemplateDTO);
	
	void delete(Long id);
	
	List<PlanTemplateDTO> getAll();
}
