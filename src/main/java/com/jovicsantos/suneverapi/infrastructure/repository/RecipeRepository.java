package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeEntity;

public interface RecipeRepository extends JpaRepository<RecipeEntity, UUID> {
  boolean existsByName(String name);
}
