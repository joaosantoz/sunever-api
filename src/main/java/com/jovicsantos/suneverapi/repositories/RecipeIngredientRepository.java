package com.jovicsantos.suneverapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovicsantos.suneverapi.models.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {

}
