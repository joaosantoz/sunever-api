package com.jovicsantos.suneverapi.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.models.Measurement;
import com.jovicsantos.suneverapi.repositories.MeasurementRepository;

@Service
public class MeasurementService {
  @Autowired
  MeasurementRepository measurementRepository;

  public Measurement save(Measurement measurement) {
    return measurementRepository.save(measurement);
  }

  public Iterable<Measurement> findAll() {
    return measurementRepository.findAll();
  }

  public boolean existsByName(String name) {
    return measurementRepository.existsByName(name);
  }

  public Optional<Measurement> findById(UUID id) {
    return measurementRepository.findById(id);
  }
}
