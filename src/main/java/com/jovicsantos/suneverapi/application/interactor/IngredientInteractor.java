package com.jovicsantos.suneverapi.application.interactor;

import com.jovicsantos.suneverapi.application.gateway.IngredientGateway;
import com.jovicsantos.suneverapi.application.usecase.IngredientUsecase;
import com.jovicsantos.suneverapi.domain.Ingredient;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IngredientInteractor implements IngredientUsecase {
	private final IngredientGateway ingredientGateway;

	public IngredientInteractor(IngredientGateway ingredientGateway) {
		this.ingredientGateway = ingredientGateway;
	}

	public Ingredient save(Ingredient ingredient) {
		if (ingredientGateway.existsByName(ingredient.getName())) {
			throw new RuntimeException("Ingredient name already exists.");
		}

		return ingredientGateway.createIngredient(ingredient);
	}

	public Ingredient find(UUID id) {
		Optional<Ingredient> optionalIngredient = ingredientGateway.findIngredient(id);

		return optionalIngredient.orElseThrow(() -> new RuntimeException("Ingredient not found."));
	}

	public List<Ingredient> findAll() {
		return ingredientGateway.findAllIngredients();
	}

	public Ingredient update(UUID id, Ingredient ingredient) {
		Ingredient ingredientToBeUpdated = find(id);
		BeanUtils.copyProperties(ingredient, ingredientToBeUpdated);

		return ingredientGateway.updateIngredient(id, ingredientToBeUpdated);
	}

	public void delete(UUID id) {
		Ingredient ingredientToBeDeleted = find(id);

		ingredientGateway.deleteIngredient(ingredientToBeDeleted.getId());
	}
}
