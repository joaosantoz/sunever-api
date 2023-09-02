package com.jovicsantos.suneverapi.api.controller;

import com.jovicsantos.suneverapi.api.dto.MeasurementDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.MeasurementEntity;
import com.jovicsantos.suneverapi.infrastructure.service.MeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
	final MeasurementService measurementService;

	public MeasurementController(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@PostMapping
	public ResponseEntity<Object> saveMeasurement(@RequestBody @Valid MeasurementDto measurementDto) {
		if (measurementService.existsByName(measurementDto.name())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This measurement already exists.");
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

		return optionalMeasurement.<ResponseEntity<Object>>map(
						measurementEntity -> ResponseEntity.status(HttpStatus.OK).body(measurementEntity)).orElseGet(() ->
						ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found."));
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