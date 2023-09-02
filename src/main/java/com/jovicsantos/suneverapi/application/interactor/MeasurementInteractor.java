package com.jovicsantos.suneverapi.application.interactor;

import com.jovicsantos.suneverapi.application.gateway.MeasurementGateway;
import com.jovicsantos.suneverapi.application.usecase.MeasurementUsecase;
import com.jovicsantos.suneverapi.domain.Measurement;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeasurementInteractor implements MeasurementUsecase {
	private final MeasurementGateway measurementGateway;

	public MeasurementInteractor(MeasurementGateway measurementGateway) {
		this.measurementGateway = measurementGateway;
	}

	public Measurement save(Measurement measurement) {
		if (measurementGateway.existsByName(measurement.getName())) {
			throw new RuntimeException("Measurement name already exists.");
		}

		if (measurementGateway.existsByAbbreviation(measurement.getAbbreviation())) {
			throw new RuntimeException("Measurement abbreviation already exists.");
		}

		return measurementGateway.createMeasurement(measurement);
	}

	public Measurement find(UUID id) {
		Optional<Measurement> optionalMeasurement = measurementGateway.findMeasurement(id);

		return optionalMeasurement.orElseThrow(() -> new RuntimeException("Measurement not found."));
	}

	public List<Measurement> findAll() {
		return measurementGateway.findAllMeasurements();
	}

	public Measurement update(UUID id, Measurement measurement) {
		Measurement measurementToBeUpdated = find(id);
		BeanUtils.copyProperties(measurement, measurementToBeUpdated);

		return measurementGateway.updateMeasurement(id, measurementToBeUpdated);
	}

	public void delete(UUID id) {
		Measurement measurementToBeDeleted = find(id);

		measurementGateway.deleteMeasurement(measurementToBeDeleted.getId());
	}
}
