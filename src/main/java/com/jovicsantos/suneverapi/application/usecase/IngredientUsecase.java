package com.jovicsantos.suneverapi.application.usecase;

import com.jovicsantos.suneverapi.domain.Ingredient;

import java.util.UUID;

public interface IngredientUsecase {
	Ingredient createIngredient(Ingredient ingredient);

	Ingredient findIngredientById(UUID id);

	Ingredient updateIngredient(Ingredient ingredient);

	Iterable<Ingredient> listAllIngredients();

	void deleteIngredientById(UUID id);
}
