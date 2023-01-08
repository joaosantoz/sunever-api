package com.jovicsantos.suneverapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.models.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
  boolean existsByName(String name);
}
