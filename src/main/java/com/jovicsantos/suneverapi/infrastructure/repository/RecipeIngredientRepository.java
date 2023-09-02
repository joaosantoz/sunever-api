package com.jovicsantos.suneverapi.infrastructure.repository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, UUID> {
}
