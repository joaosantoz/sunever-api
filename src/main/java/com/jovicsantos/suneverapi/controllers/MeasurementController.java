package com.jovicsantos.suneverapi.controllers;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovicsantos.suneverapi.dtos.MeasurementDto;
import com.jovicsantos.suneverapi.models.Measurement;
import com.jovicsantos.suneverapi.services.MeasurementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
  @Autowired
  MeasurementService measurementService;

  @PostMapping
  public ResponseEntity<Object> saveMeasurement(@RequestBody @Valid MeasurementDto measurementDto) {
    if (measurementService.existsByName(measurementDto.name())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Conflict: This measurement already exists.");
    }

    var measurementModel = new Measurement();
    BeanUtils.copyProperties(measurementDto, measurementModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.save(measurementModel));
  }

  @GetMapping
  public ResponseEntity<Iterable<Measurement>> getAllMeasurements() {
    return ResponseEntity.status(HttpStatus.OK).body(measurementService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMeasurementById(@PathVariable(name = "id") UUID id) {
    var optionalMeasurement = measurementService.findById(id);

    if (!optionalMeasurement.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(optionalMeasurement.get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMeasurementById(@PathVariable(name = "id") UUID id) {
    var optionalMeasurement = measurementService.findById(id);

    if (!optionalMeasurement.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
    }

    measurementService.deleteById(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Measurement deleted successfully.");
  }
}