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

import com.jovicsantos.suneverapi.api.dto.MeasurementDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.MeasurementEntity;
import com.jovicsantos.suneverapi.infrastructure.service.MeasurementService;

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

    var measurementModel = new MeasurementEntity();
    BeanUtils.copyProperties(measurementDto, measurementModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.save(measurementModel));
  }

  @GetMapping
  public ResponseEntity<Iterable<MeasurementEntity>> getAllMeasurements() {
    return ResponseEntity.status(HttpStatus.OK).body(measurementService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMeasurementById(@PathVariable UUID id) {
    var optionalMeasurement = measurementService.findById(id);

    if (optionalMeasurement.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(optionalMeasurement.get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMeasurementById(@PathVariable UUID id) {
    var optionalMeasurement = measurementService.findById(id);

    if (optionalMeasurement.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
    }

    measurementService.deleteById(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Measurement deleted successfully.");
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMeasurementById(@PathVariable UUID id,
      @RequestBody @Valid MeasurementDto measurementDto) {
    var optionalMeasurement = measurementService.findById(id);

    if (optionalMeasurement.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
    }

    var measurementModel = new MeasurementEntity();
    BeanUtils.copyProperties(measurementDto, measurementModel);
    measurementModel.setId(optionalMeasurement.get().getId());

    return ResponseEntity.status(HttpStatus.OK).body(measurementService.save(measurementModel));
  }
}