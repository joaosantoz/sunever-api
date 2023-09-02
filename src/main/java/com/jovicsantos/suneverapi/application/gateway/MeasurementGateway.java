package com.jovicsantos.suneverapi.application.gateway;

import com.jovicsantos.suneverapi.domain.Measurement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeasurementGateway {
	Measurement createMeasurement(Measurement measurement);

	Optional<Measurement> findMeasurement(UUID id);

	Measurement updateMeasurement(Measurement measurement);

	List<Measurement> findAllMeasurements();

	void deleteMeasurement(UUID id);
}
