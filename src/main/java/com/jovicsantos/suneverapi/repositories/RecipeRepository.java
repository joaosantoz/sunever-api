package com.jovicsantos.suneverapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.models.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
  boolean existsByName(String name);
}
