package com.jovicsantos.suneverapi.infrastructure.persistance.mapper;

import com.jovicsantos.suneverapi.domain.Measurement;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.MeasurementEntity;

public class MeasurementMapper {
	public MeasurementEntity toEntity(Measurement measurement) {
		return new MeasurementEntity(measurement.getId(), measurement.getName(), measurement.getAbbreviation());
	}

	public Measurement toDomain(MeasurementEntity measurementEntity) {
		return new Measurement(measurementEntity.getId(), measurementEntity.getName(), measurementEntity.getAbbreviation());
	}
}
