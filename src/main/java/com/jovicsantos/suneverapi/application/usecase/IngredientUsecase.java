package com.jovicsantos.suneverapi.application.usecase;

import com.jovicsantos.suneverapi.domain.Ingredient;

import java.util.List;
import java.util.UUID;

public interface IngredientUsecase {
	Ingredient save(Ingredient ingredient);

	Ingredient find(UUID id);

	List<Ingredient> findAll();

	Ingredient update(UUID id, Ingredient ingredient);

	void delete(UUID id);
}
