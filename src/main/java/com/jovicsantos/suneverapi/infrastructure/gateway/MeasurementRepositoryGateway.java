package com.jovicsantos.suneverapi.infrastructure.gateway;

import com.jovicsantos.suneverapi.application.gateway.MeasurementGateway;
import com.jovicsantos.suneverapi.domain.Measurement;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.MeasurementEntity;
import com.jovicsantos.suneverapi.infrastructure.persistance.mapper.MeasurementMapper;
import com.jovicsantos.suneverapi.infrastructure.repository.MeasurementRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeasurementRepositoryGateway implements MeasurementGateway {
	private final MeasurementRepository measurementRepository;
	private final MeasurementMapper measurementMapper;

	public MeasurementRepositoryGateway(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
		this.measurementRepository = measurementRepository;
		this.measurementMapper = measurementMapper;
	}

	@Override
	public Measurement createMeasurement(Measurement measurement) {
		MeasurementEntity measurementEntity = measurementMapper.toEntity(measurement);
		MeasurementEntity measurementSaved = measurementRepository.save(measurementEntity);

		return measurementMapper.toDomain(measurementSaved);
	}

	@Override
	public Optional<Measurement> findMeasurement(UUID id) {
		Optional<MeasurementEntity> measurementEntity = measurementRepository.findById(id);

		return measurementEntity.map(measurementMapper::toDomain);
	}

	@Override
	public List<Measurement> findAllMeasurements() {
		List<MeasurementEntity> measurementEntities = measurementRepository.findAll();
		if (measurementEntities.isEmpty()) {
			return Collections.emptyList();
		}
		return measurementEntities.stream().map(measurementMapper::toDomain).toList();
	}

	@Override
	public Measurement updateMeasurement(UUID id, Measurement measurement) {
		MeasurementEntity measurementEntity = measurementMapper.toEntity(measurement);
		MeasurementEntity measurementUpdated = measurementRepository.save(measurementEntity);

		return measurementMapper.toDomain(measurementUpdated);
	}

	@Override
	public void deleteMeasurement(UUID id) {
		measurementRepository.deleteById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return measurementRepository.existsByName(name);
	}

	@Override
	public boolean existsByAbbreviation(String abbreviation) {
		return measurementRepository.existsByAbbreviation(abbreviation);
	}
}
