package com.jovicsantos.suneverapi.application.gateway;

import com.jovicsantos.suneverapi.domain.Measurement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeasurementGateway {
	Measurement createMeasurement(Measurement measurement);

	Optional<Measurement> findMeasurement(UUID id);

	List<Measurement> findAllMeasurements();

	Measurement updateMeasurement(UUID id, Measurement measurement);

	void deleteMeasurement(UUID id);

	boolean existsByName(String name);

	boolean existsByAbbreviation(String abbreviation);
}
