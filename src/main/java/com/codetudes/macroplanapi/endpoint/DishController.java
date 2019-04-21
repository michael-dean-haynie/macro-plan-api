package com.codetudes.macroplanapi.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;
import com.codetudes.macroplanapi.service.DishService;

@RestController
@RequestMapping("dish")
public class DishController {
	@Autowired
	private DishService dishService;
	
	@GetMapping("/{id}")
	public DishTemplateDTO get(@PathVariable("id") Long id) {
		return dishService.get(id);
	}
}
