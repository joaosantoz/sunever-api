package com.jovicsantos.suneverapi.application.usecase;

import com.jovicsantos.suneverapi.domain.Measurement;

import java.util.Optional;
import java.util.UUID;

public interface MeasurementUsecase {
	Measurement create(Measurement measurement);

	Optional<Measurement> find(UUID id);

	Measurement update(Measurement measurement);

	Iterable<Measurement> findAll();

	void delete(UUID id);
}
