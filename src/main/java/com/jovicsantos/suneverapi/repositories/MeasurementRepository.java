package com.jovicsantos.suneverapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.models.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
  boolean existsByName(String name);
}
