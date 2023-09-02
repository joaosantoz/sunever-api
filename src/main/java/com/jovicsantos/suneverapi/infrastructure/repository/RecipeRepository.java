package com.jovicsantos.suneverapi.infrastructure.repository;

import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<RecipeEntity, UUID> {
	boolean existsByName(String name);
}
