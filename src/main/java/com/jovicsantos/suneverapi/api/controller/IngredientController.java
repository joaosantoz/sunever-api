package com.jovicsantos.suneverapi.api.controller;

import com.jovicsantos.suneverapi.api.dto.IngredientDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.service.IngredientService;
import com.jovicsantos.suneverapi.infrastructure.service.MeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	final IngredientService ingredientService;
	final MeasurementService measurementService;

	public IngredientController(IngredientService ingredientService, MeasurementService measurementService) {
		this.ingredientService = ingredientService;
		this.measurementService = measurementService;
	}

	@PostMapping
	public ResponseEntity<Object> saveIngredient(@RequestBody @Valid IngredientDto ingredientDto) {
		if (ingredientService.existsByName(ingredientDto.name())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
							.body("Conflict: This ingredient already exists.");
		}

		var ingredientModel = new IngredientEntity();
		BeanUtils.copyProperties(ingredientDto, ingredientModel);

		var optionalMeasurement = measurementService.findById(ingredientDto.measurementId());

		if (optionalMeasurement.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement ID not found.");
		}

		ingredientModel.setMeasurement(optionalMeasurement.get());

		return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.save(ingredientModel));
	}

	@GetMapping
	public ResponseEntity<Object> getAllIngredients() {
		return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getIngredientById(@PathVariable UUID id) {
		var optionalIngredient = ingredientService.findById(id);

		return optionalIngredient.<ResponseEntity<Object>>map(ingredientEntity ->
						ResponseEntity.status(HttpStatus.OK).body(ingredientEntity)).orElseGet(() ->
						ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found."));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteIngredientById(@PathVariable UUID id) {
		var optionalIngredient = ingredientService.findById(id);

		if (optionalIngredient.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found.");
		}

		ingredientService.deleteById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ingredient deleted successfully.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateIngredientById(@PathVariable UUID id,
																										 @RequestBody @Valid IngredientDto ingredientDto) {
		var optionalIngredient = ingredientService.findById(id);
		if (optionalIngredient.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found.");
		}

		var optionalMeasurement = measurementService.findById(ingredientDto.measurementId());
		if (optionalMeasurement.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement ID not found.");
		}

		var ingredientModel = new IngredientEntity();
		BeanUtils.copyProperties(ingredientDto, ingredientModel);
		ingredientModel.setId(id);
		ingredientModel.setMeasurement(optionalMeasurement.get());

		return ResponseEntity.status(HttpStatus.OK).body(ingredientService.save(ingredientModel));
	}
}
