package com.jovicsantos.suneverapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.models.MeasurementModel;
import com.jovicsantos.suneverapi.repositories.MeasurementRepository;

@Service
public class MeasurementService {
  @Autowired
  MeasurementRepository measurementRepository;

  public MeasurementModel save(MeasurementModel measurement) {
    return measurementRepository.save(measurement);
  }

  public Iterable<MeasurementModel> findAll() {
    return measurementRepository.findAll();
  }

  public boolean existsByName(String name) {
    return measurementRepository.existsByName(name);
  }
}
