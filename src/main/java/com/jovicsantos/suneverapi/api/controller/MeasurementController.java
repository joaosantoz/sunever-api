package com.jovicsantos.suneverapi.api.controller;

import com.jovicsantos.suneverapi.api.dto.MeasurementDto;
import com.jovicsantos.suneverapi.application.input.MeasurementInput;
import com.jovicsantos.suneverapi.application.interactor.MeasurementInteractor;
import com.jovicsantos.suneverapi.application.output.MeasurementOutput;
import com.jovicsantos.suneverapi.domain.Measurement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
	private final MeasurementInteractor measurementInteractor;
	private final MeasurementDto measurementDto;

	public MeasurementController(MeasurementInteractor measurementInteractor, MeasurementDto measurementDto) {
		this.measurementInteractor = measurementInteractor;
		this.measurementDto = measurementDto;
	}

	@PostMapping
	public ResponseEntity<MeasurementOutput> saveMeasurement(@RequestBody @Valid MeasurementInput measurementInput) {
		Measurement measurementDomain = measurementDto.toDomain(measurementInput);
		Measurement measurementSaved = measurementInteractor.save(measurementDomain);

		return ResponseEntity.status(HttpStatus.CREATED).body(measurementDto.toOutput(measurementSaved));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MeasurementOutput> findMeasurement(@PathVariable UUID id) {
		Measurement measurement = measurementInteractor.find(id);

		return ResponseEntity.status(HttpStatus.OK).body(measurementDto.toOutput(measurement));
	}

	@GetMapping
	public ResponseEntity<List<MeasurementOutput>> getAllMeasurements() {
		List<Measurement> allMeasurements = measurementInteractor.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(allMeasurements.stream().map(measurementDto::toOutput).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<MeasurementOutput> updateMeasurementById(
					@PathVariable UUID id,
					@RequestBody @Valid MeasurementInput measurementInput) {
		Measurement measurementDomain = measurementDto.toDomain(measurementInput);
		measurementDomain.setId(id);
		
		Measurement measurementUpdated = measurementInteractor.update(id, measurementDomain);

		return ResponseEntity.status(HttpStatus.OK).body(measurementDto.toOutput(measurementUpdated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMeasurementById(@PathVariable UUID id) {
		measurementInteractor.delete(id);

		return ResponseEntity.status(HttpStatus.OK).body("Measurement " + id.toString() + " deleted successfully");
	}
}