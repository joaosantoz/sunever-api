package com.jovicsantos.suneverapi.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeIngredientEntity;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, UUID> {

}
