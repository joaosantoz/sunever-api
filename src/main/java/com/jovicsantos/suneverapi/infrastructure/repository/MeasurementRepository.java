package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.domain.entity.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
  boolean existsByName(String name);
}
