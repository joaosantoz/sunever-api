package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.domain.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
  boolean existsByName(String name);
}
