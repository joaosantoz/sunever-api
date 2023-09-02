package com.jovicsantos.suneverapi.infrastructure.service;

import com.jovicsantos.suneverapi.infrastructure.db.entity.MeasurementEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurementService {
	final MeasurementRepository measurementRepository;

	public MeasurementService(MeasurementRepository measurementRepository) {
		this.measurementRepository = measurementRepository;
	}

	public MeasurementEntity save(MeasurementEntity measurement) {
		return measurementRepository.save(measurement);
	}

	public Iterable<MeasurementEntity> findAll() {
		return measurementRepository.findAll();
	}

	public boolean existsByName(String name) {
		return measurementRepository.existsByName(name);
	}

	public Optional<MeasurementEntity> findById(UUID id) {
		return measurementRepository.findById(id);
	}

	public void deleteById(UUID id) {
		measurementRepository.deleteById(id);
	}
}
