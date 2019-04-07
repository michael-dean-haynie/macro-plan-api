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

import com.codetudes.macroplanapi.contract.FoodDTO;
import com.codetudes.macroplanapi.contract.UnitDTO;
import com.codetudes.macroplanapi.db.enums.UnitEnum;
import com.codetudes.macroplanapi.db.enums.UnitSystemEnum;
import com.codetudes.macroplanapi.db.enums.UnitTypeEnum;
import com.codetudes.macroplanapi.service.FoodService;
import com.codetudes.macroplanapi.service.impl.UnitServiceImpl;

@RestController
@RequestMapping("food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@PostMapping
	public FoodDTO createFood(@Valid @RequestBody FoodDTO foodDTO) {
		return foodService.createFood(foodDTO);
	}
	
	@GetMapping("/{id}")
	public FoodDTO getFood(@PathVariable("id") Long id) {
		return foodService.getFood(id);
	}
	
	@PutMapping()
	public FoodDTO updateFood(@Valid @RequestBody FoodDTO foodDTO) {
		return foodService.updateFood(foodDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFood(@PathVariable("id") Long id){
		foodService.deleteFood(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
