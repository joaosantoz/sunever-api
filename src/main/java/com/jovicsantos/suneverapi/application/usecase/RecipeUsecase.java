package com.jovicsantos.suneverapi.application.usecase;

import com.jovicsantos.suneverapi.domain.Recipe;

import java.math.BigDecimal;
import java.util.UUID;

public interface RecipeUsecase {
	Recipe createRecipe(Recipe recipe);

	Recipe findRecipeById(UUID id);

	Recipe updateRecipe(Recipe recipe);

	Iterable<Recipe> findAllRecipes();

	void deleteRecipeById(UUID id);

	BigDecimal calculateRecipePortionProductionCost();

	BigDecimal calculateRecipeSellingPrice();

	BigDecimal calculateRecipeProductionCost();
}
