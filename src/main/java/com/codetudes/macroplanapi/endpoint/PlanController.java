package com.codetudes.macroplanapi.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetudes.macroplanapi.contract.plan.PlanTemplateDTO;
import com.codetudes.macroplanapi.service.PlanService;

@RestController
@RequestMapping("plan")
public class PlanController {
	@Autowired
	private PlanService planService;

	@PostMapping
	public PlanTemplateDTO create(@Valid @RequestBody PlanTemplateDTO planTemplateDTO) {
		return planService.create(planTemplateDTO);
	}

	@GetMapping("/{id}")
	public PlanTemplateDTO get(@PathVariable("id") Long id) {
		return planService.get(id);
	}

	@PutMapping()
	public PlanTemplateDTO update(@Valid @RequestBody PlanTemplateDTO planTemplateDTO) {
		return planService.update(planTemplateDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		planService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
