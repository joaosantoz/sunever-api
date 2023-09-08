package com.jovicsantos.suneverapi.application.gateway;

import com.jovicsantos.suneverapi.domain.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientGateway {
	Ingredient createIngredient(Ingredient ingredient);

	Optional<Ingredient> findIngredient(UUID id);

	List<Ingredient> findAllIngredients();

	Ingredient updateIngredient(UUID id, Ingredient ingredient);

	void deleteIngredient(UUID id);

	boolean existsByName(String name);
}
