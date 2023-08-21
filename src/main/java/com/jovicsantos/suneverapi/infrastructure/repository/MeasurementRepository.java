package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.MeasurementEntity;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, UUID> {
  boolean existsByName(String name);
}
