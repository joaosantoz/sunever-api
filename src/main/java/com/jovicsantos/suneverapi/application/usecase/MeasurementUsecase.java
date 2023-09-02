package com.jovicsantos.suneverapi.application.usecase;

import com.jovicsantos.suneverapi.domain.Measurement;

import java.util.UUID;

public interface MeasurementUsecase {
	Measurement save(Measurement measurement);

	Measurement find(UUID id);

	Iterable<Measurement> findAll();

	Measurement update(UUID id, Measurement measurement);

	void delete(UUID id);
}
