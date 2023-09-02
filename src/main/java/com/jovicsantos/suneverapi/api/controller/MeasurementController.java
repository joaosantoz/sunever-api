package com.jovicsantos.suneverapi.api.controller;

import com.jovicsantos.suneverapi.api.dto.MeasurementDtoMapper;
import com.jovicsantos.suneverapi.application.input.MeasurementInput;
import com.jovicsantos.suneverapi.application.interactor.MeasurementInteractor;
import com.jovicsantos.suneverapi.application.output.MeasurementOutput;
import com.jovicsantos.suneverapi.domain.Measurement;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
	private final MeasurementInteractor measurementInteractor;
	private final MeasurementDtoMapper measurementDtoMapper;

	public MeasurementController(MeasurementInteractor measurementInteractor, MeasurementDtoMapper measurementDtoMapper) {
		this.measurementInteractor = measurementInteractor;
		this.measurementDtoMapper = measurementDtoMapper;
	}

	@PostMapping
	public ResponseEntity<MeasurementOutput> saveMeasurement(@RequestBody @Valid MeasurementInput measurementInput) {
		Measurement measurementDomain = measurementDtoMapper.toDomain(measurementInput);
		Measurement measurement = measurementInteractor.create(measurementDomain);

		return ResponseEntity.status(HttpStatus.CREATED).body(measurementDtoMapper.toOutput(measurement));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MeasurementOutput> getMeasurementById(@PathVariable UUID id) {
		var optionalMeasurement = measurementInteractor.find(id);

		return optionalMeasurement.map(measurement -> ResponseEntity.status(HttpStatus.OK).body(measurementDtoMapper.toOutput(measurement)))
						.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateMeasurementById(@PathVariable UUID id, @RequestBody @Valid MeasurementInput measurementInput) {
		var optionalMeasurement = measurementInteractor.find(id);

		if (optionalMeasurement.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found.");
		}

		Measurement measurementDomain = measurementDtoMapper.toDomain(measurementInput);
		Measurement measurement = optionalMeasurement.get();
		BeanUtils.copyProperties(measurementDomain, measurement);
		measurement = measurementInteractor.update(measurement);

		return ResponseEntity.status(HttpStatus.OK).body(measurementDtoMapper.toOutput(measurement));
	}

	@GetMapping
	public ResponseEntity<List<MeasurementOutput>> getAllMeasurements() {
		var measurements = measurementInteractor.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(measurements.stream().map(measurementDtoMapper::toOutput).toList());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MeasurementOutput> deleteMeasurementById(@PathVariable UUID id) {
		var optionalMeasurement = measurementInteractor.find(id);

		if (optionalMeasurement.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		measurementInteractor.delete(id);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}