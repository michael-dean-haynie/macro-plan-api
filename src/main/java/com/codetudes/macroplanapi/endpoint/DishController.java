package com.codetudes.macroplanapi.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codetudes.macroplanapi.contract.dish.DishTemplateDTO;
import com.codetudes.macroplanapi.service.DishService;

@RestController
@RequestMapping("dish")
public class DishController {
	@Autowired
	private DishService dishService;
	
	@PostMapping
	public DishTemplateDTO create(@Valid @RequestBody DishTemplateDTO dishTemplateDTO) {
		return dishService.create(dishTemplateDTO);
	}
	
	@GetMapping("/{id}")
	public DishTemplateDTO get(@PathVariable("id") Long id) {
		return dishService.get(id);
	}
	
	@PutMapping()
	public DishTemplateDTO update(@Valid @RequestBody DishTemplateDTO dishTemplateDTO) {
		return dishService.update(dishTemplateDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		dishService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(params = {"searchTerm", "sortField", "sortDirection"})
	public List<DishTemplateDTO> getViaSearchAndSort(@RequestParam("searchTerm") String searchTerm,
			@RequestParam("sortField") String sortField, @RequestParam("sortDirection") Sort.Direction sortDirection) {
		return dishService.getViaSearchAndSort(searchTerm, sortField, sortDirection);
	}
}
