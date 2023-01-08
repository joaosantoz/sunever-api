package com.jovicsantos.suneverapi.services;

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
}
