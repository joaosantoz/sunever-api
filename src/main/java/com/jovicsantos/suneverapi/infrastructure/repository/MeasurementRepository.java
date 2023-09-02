package com.jovicsantos.suneverapi.infrastructure.repository;

import com.jovicsantos.suneverapi.infrastructure.persistance.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, UUID> {
}
