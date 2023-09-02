//package com.jovicsantos.suneverapi.api.controller;
//
//import com.jovicsantos.suneverapi.api.dto.IngredientDto;
//import com.jovicsantos.suneverapi.application.interactor.IngredientInteractor;
//import com.jovicsantos.suneverapi.application.interactor.MeasurementInteractor;
//import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;
//import jakarta.validation.Valid;
//import org.springframework.beans.BeanUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/ingredients")
//public class IngredientController {
//	private final IngredientInteractor ingredientInteractor;
//	private final MeasurementInteractor measurementInteractor;
//
//	public IngredientController(IngredientInteractor ingredientInteractor, MeasurementInteractor measurementInteractor) {
//		this.ingredientInteractor = ingredientInteractor;
//		this.measurementInteractor = measurementInteractor;
//	}
//
//	@PostMapping
//	public ResponseEntity<Object> saveIngredient(@RequestBody @Valid IngredientDto ingredientDto) {
//		if (ingredientInteractor.existsByName(ingredientDto.name())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT)
//							.body("Conflict: This ingredient already exists.");
//		}
//
//		var ingredientModel = new IngredientEntity();
//		BeanUtils.copyProperties(ingredientDto, ingredientModel);
//
//		var optionalMeasurement = measurementInteractor.findById(ingredientDto.measurementId());
//
//		if (optionalMeasurement.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement ID not found.");
//		}
//
//		ingredientModel.setMeasurement(optionalMeasurement.get());
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(ingredientInteractor.save(ingredientModel));
//	}
//
//	@GetMapping
//	public ResponseEntity<Object> getAllIngredients() {
//		return ResponseEntity.status(HttpStatus.OK).body(ingredientInteractor.findAll());
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<Object> getIngredientById(@PathVariable UUID id) {
//		var optionalIngredient = ingredientInteractor.findById(id);
//
//		return optionalIngredient.<ResponseEntity<Object>>map(ingredientEntity ->
//						ResponseEntity.status(HttpStatus.OK).body(ingredientEntity)).orElseGet(() ->
//						ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found."));
//
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Object> deleteIngredientById(@PathVariable UUID id) {
//		var optionalIngredient = ingredientInteractor.findById(id);
//
//		if (optionalIngredient.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found.");
//		}
//
//		ingredientInteractor.deleteById(id);
//
//		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ingredient deleted successfully.");
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<Object> updateIngredientById(@PathVariable UUID id,
//																										 @RequestBody @Valid IngredientDto ingredientDto) {
//		var optionalIngredient = ingredientInteractor.findById(id);
//		if (optionalIngredient.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found.");
//		}
//
//		var optionalMeasurement = measurementInteractor.findById(ingredientDto.measurementId());
//		if (optionalMeasurement.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement ID not found.");
//		}
//
//		var ingredientModel = new IngredientEntity();
//		BeanUtils.copyProperties(ingredientDto, ingredientModel);
//		ingredientModel.setId(id);
//		ingredientModel.setMeasurement(optionalMeasurement.get());
//
//		return ResponseEntity.status(HttpStatus.OK).body(ingredientInteractor.save(ingredientModel));
//	}
//}
