package com.jovicsantos.suneverapi.application.interactor;

import com.jovicsantos.suneverapi.application.gateway.MeasurementGateway;
import com.jovicsantos.suneverapi.application.usecase.MeasurementUsecase;
import com.jovicsantos.suneverapi.domain.Measurement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeasurementInteractor implements MeasurementUsecase {
	private final MeasurementGateway measurementGateway;

	public MeasurementInteractor(MeasurementGateway measurementGateway) {
		this.measurementGateway = measurementGateway;
	}

	public Measurement create(Measurement measurement) {
		return measurementGateway.createMeasurement(measurement);
	}

	public Optional<Measurement> find(UUID id) {
		return measurementGateway.findMeasurement(id);
	}

	public Measurement update(Measurement measurement) {
		return measurementGateway.updateMeasurement(measurement);
	}

	public List<Measurement> findAll() {
		return measurementGateway.findAllMeasurements();
	}

	public void delete(UUID id) {
		measurementGateway.deleteMeasurement(id);
	}
}
