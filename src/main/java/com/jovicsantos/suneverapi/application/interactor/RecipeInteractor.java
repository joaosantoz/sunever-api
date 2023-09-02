package com.jovicsantos.suneverapi.application.interactor;

import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.RecipeEntity;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.RecipeIngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.RecipeIngredientRepository;
import com.jovicsantos.suneverapi.infrastructure.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeInteractor {
	private final RecipeRepository recipeRepository;

	private final RecipeIngredientRepository recipeIngredientRepository;

	private final IngredientInteractor ingredientInteractor;

	public RecipeInteractor(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository, IngredientInteractor ingredientInteractor) {
		this.recipeRepository = recipeRepository;
		this.recipeIngredientRepository = recipeIngredientRepository;
		this.ingredientInteractor = ingredientInteractor;
	}

	public boolean existsByName(String name) {
		return recipeRepository.existsByName(name);
	}

	public RecipeEntity save(RecipeEntity recipe, List<RecipeIngredientEntity> ingredients) {
		RecipeEntity savedRecipe = recipeRepository.save(recipe);

		List<RecipeIngredientEntity> recipeIngredientList = new ArrayList<>();

		for (RecipeIngredientEntity ingredient : ingredients) {
			ingredient.setRecipe(savedRecipe);

			recipeIngredientList.add(ingredient);
		}

		recipeIngredientRepository.saveAll(recipeIngredientList);

		return savedRecipe;
	}

	public RecipeEntity calculateRecipeProductionCost(UUID recipeId) throws Exception {
		Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isEmpty()) {
			throw new Exception("Recipe " + recipeId + " not found.");
		}

		var recipe = recipeOptional.get();

		var recipeProductionCost = this.calculateAllRecipeIngredientsCost(recipe);

		recipe.setRecipeProductionCost(recipeProductionCost.setScale(2, RoundingMode.HALF_UP));

		return recipe;
	}

	private BigDecimal calculateAllRecipeIngredientsCost(RecipeEntity recipe) throws Exception {
		List<BigDecimal> ingredientsCostList = new ArrayList<>();

		for (RecipeIngredientEntity recipeIngredient : recipe.getIngredientList()) {

			UUID ingredientId = recipeIngredient.getId();

			Optional<IngredientEntity> ingredientOptional = ingredientInteractor.findById(ingredientId);

			if (ingredientOptional.isEmpty()) {
				throw new Exception("Ingredient " + ingredientId + " not found.");
			}

			var ingredient = ingredientOptional.get();

			BigDecimal ingredientQuantityPerRecipe = recipeIngredient.getQuantity();
			BigDecimal ingredientQuantityPerMeasure = ingredient.getQuantityPerMeasure();
			BigDecimal ingredientPrice = ingredient.getPrice();

			var ingredientCost = this.calculateIngredientCost(ingredientQuantityPerRecipe, ingredientQuantityPerMeasure, ingredientPrice);

			ingredientsCostList.add(ingredientCost);
		}

		return (ingredientsCostList).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calculateIngredientCost(BigDecimal ingredientQuantityPerRecipe, BigDecimal ingredientQuantityPerMeasure, BigDecimal ingredientPrice) {
		var ingredientCostOfOneMeasure = ingredientQuantityPerMeasure.divide(ingredientPrice, RoundingMode.HALF_UP);

		return ingredientQuantityPerRecipe.divide(ingredientCostOfOneMeasure, RoundingMode.HALF_UP);
	}

	public BigDecimal calculateRecipeSellingPrice(UUID recipeId, BigDecimal profitPercentage) throws Exception {
		this.calculateRecipeProductionCost(recipeId);

		if (!this.profitPercentageIsGreaterThanZero(profitPercentage)) {
			throw new Exception("The field profitPercentage must be greater than zero.");
		}

		Optional<RecipeEntity> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isEmpty()) {
			throw new Exception("Recipe " + recipeId + " not found.");
		}

		var recipe = recipeOptional.get();

		var recipeProductionCost = recipe.getRecipeProductionCost();

		BigDecimal recipeProfit = recipeProductionCost.multiply(profitPercentage).divide((BigDecimal.valueOf(100)), RoundingMode.HALF_UP);

		BigDecimal recipeSellingPrice = recipeProductionCost.add(recipeProfit);

		return recipeSellingPrice.setScale(2, RoundingMode.HALF_UP);
	}

	public RecipeEntity calculatePortionProductionCost(UUID recipeId) throws Exception {
		var recipeCalculated = this.calculateRecipeProductionCost(recipeId);
		var portions = recipeCalculated.getPortions();
		var recipeProductionCost = recipeCalculated.getRecipeProductionCost();

		BigDecimal portionProductionCost = recipeProductionCost.divide(BigDecimal.valueOf(portions), RoundingMode.HALF_UP);

		recipeCalculated.setPortionProductionCost(portionProductionCost.setScale(2, RoundingMode.HALF_UP));

		return recipeCalculated;
	}

	public BigDecimal calculatePortionSellingPrice(UUID recipeId, BigDecimal profitPercentage) throws Exception {
		var portionProductionCost = this.calculatePortionProductionCost(recipeId).getPortionProductionCost();

		BigDecimal portionProfit = portionProductionCost.multiply(profitPercentage).divide((BigDecimal.valueOf(100)), RoundingMode.HALF_UP);

		BigDecimal portionSellingPrice = portionProductionCost.add(portionProfit);

		return portionSellingPrice.setScale(2, RoundingMode.HALF_UP);
	}

	private boolean profitPercentageIsGreaterThanZero(BigDecimal profitPercentage) {
		return profitPercentage.compareTo(new BigDecimal("0.0")) > 0;
	}
}
