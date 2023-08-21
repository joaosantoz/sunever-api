package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;

public interface IngredientRepository extends JpaRepository<IngredientEntity, UUID> {
  boolean existsByName(String name);
}
