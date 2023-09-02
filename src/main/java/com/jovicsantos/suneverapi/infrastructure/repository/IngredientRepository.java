package com.jovicsantos.suneverapi.infrastructure.repository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<IngredientEntity, UUID> {
	boolean existsByName(String name);
}
