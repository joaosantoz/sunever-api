package com.jovicsantos.suneverapi.infrastructure.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.infrastructure.db.entity.MeasurementEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.MeasurementRepository;

@Service
public class MeasurementService {
  @Autowired
  MeasurementRepository measurementRepository;

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
