package com.jovicsantos.suneverapi.api.controller;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
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

import com.jovicsantos.suneverapi.api.dto.IngredientDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.service.IngredientService;
import com.jovicsantos.suneverapi.infrastructure.service.MeasurementService;

import jakarta.validation.Valid;
import lombok.var;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
  @Autowired
  IngredientService ingredientService;
  @Autowired
  MeasurementService measurementService;

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

    if (optionalIngredient.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(optionalIngredient.get());
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
