package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.domain.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
  boolean existsByName(String name);
}
