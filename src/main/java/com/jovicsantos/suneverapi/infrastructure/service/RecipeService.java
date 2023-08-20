package com.jovicsantos.suneverapi.infrastructure.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.domain.entity.Ingredient;
import com.jovicsantos.suneverapi.domain.entity.Recipe;
import com.jovicsantos.suneverapi.domain.entity.RecipeIngredient;
import com.jovicsantos.suneverapi.infrastructure.repository.RecipeIngredientRepository;
import com.jovicsantos.suneverapi.infrastructure.repository.RecipeRepository;

@Service
public class RecipeService {
  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeIngredientRepository recipeIngredientRepository;

  @Autowired
  IngredientService ingredientService;

  public boolean existsByName(String name) {
    return recipeRepository.existsByName(name);
  }

  public Recipe save(Recipe recipe, List<RecipeIngredient> ingredients) {
    Recipe savedRecipe = recipeRepository.save(recipe);

    List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    for (RecipeIngredient ingredient : ingredients) {
      RecipeIngredient ingredientUpdated = ingredient;
      ingredientUpdated.setRecipe(savedRecipe);

      recipeIngredientList.add(ingredientUpdated);
    }

    for (RecipeIngredient recipeIngredient : recipeIngredientList) {
      recipeIngredientRepository.save(recipeIngredient);
    }

    return savedRecipe;
  }

  public Recipe calculateRecipeProductionCost(UUID recipeId) throws Exception {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (!recipeOptional.isPresent()) {
      throw new Exception("Recipe " + recipeId + " not found.");
    }

    var recipe = recipeOptional.get();

    var recipeProductionCost = this.calculateAllRecipeIngredientsCost(recipe);

    recipe.setRecipeProductionCost(recipeProductionCost.setScale(2, RoundingMode.HALF_UP));

    return recipe;
  }

  private BigDecimal calculateAllRecipeIngredientsCost(Recipe recipe) throws Exception {
    List<BigDecimal> ingredientsCostList = new ArrayList<>();

    for (RecipeIngredient recipeIngredient : recipe.getIngredientList()) {

      UUID ingredientId = recipeIngredient.getId();

      Optional<Ingredient> ingredientOptional = ingredientService.findById(ingredientId);

      if (!ingredientOptional.isPresent()) {
        throw new Exception("Ingredient " + ingredientId + " not found.");
      }

      var ingredient = ingredientOptional.get();

      BigDecimal ingredientQuantityPerRecipe = recipeIngredient.getQuantity();
      BigDecimal ingredientQuantityPerMeasure = ingredient.getQuantityPerMeasure();
      BigDecimal ingredientPrice = ingredient.getPrice();

      var ingredientCost = this.calculateIngredientCost(
          ingredientQuantityPerRecipe,
          ingredientQuantityPerMeasure,
          ingredientPrice);

      ingredientsCostList.add(ingredientCost);
    }

    var ingredientsCostsSum = Arrays.asList((ingredientsCostList).stream().reduce(BigDecimal.ZERO, BigDecimal::add))
        .get(0);

    return ingredientsCostsSum;
  }

  private BigDecimal calculateIngredientCost(
      BigDecimal ingredientQuantityPerRecipe,
      BigDecimal ingredientQuantityPerMeasure,
      BigDecimal ingredientPrice) {
    var ingredientCostOfOneMeasure = ingredientQuantityPerMeasure.divide(ingredientPrice, RoundingMode.HALF_UP);
    var ingredientCostRecipe = ingredientQuantityPerRecipe.divide(ingredientCostOfOneMeasure, RoundingMode.HALF_UP);

    return ingredientCostRecipe;
  }

  public BigDecimal calculateRecipeSellingPrice(UUID recipeId, BigDecimal profitPercentage) throws Exception {
    this.calculateRecipeProductionCost(recipeId);

    if (!this.profitPercentageIsGreaterThanZero(profitPercentage)) {
      throw new Exception("The field profitPercentage must be greater than zero.");
    }

    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (!recipeOptional.isPresent()) {
      throw new Exception("Recipe " + recipeId + " not found.");
    }

    var recipe = recipeOptional.get();

    var recipeProductionCost = recipe.getRecipeProductionCost();

    BigDecimal recipeProfit = recipeProductionCost.multiply(profitPercentage).divide(
        (BigDecimal.valueOf(100)), RoundingMode.HALF_UP);

    BigDecimal recipeSellingPrice = recipeProductionCost.add(recipeProfit);

    return recipeSellingPrice.setScale(2, RoundingMode.HALF_UP);
  }

  public Recipe calculatePortionProductionCost(UUID recipeId) throws Exception {
    var recipeCalculated = this.calculateRecipeProductionCost(recipeId);
    var portions = recipeCalculated.getPortions();
    var recipeProductionCost = recipeCalculated.getRecipeProductionCost();

    BigDecimal portionProductionCost = recipeProductionCost.divide(BigDecimal.valueOf(portions), RoundingMode.HALF_UP);

    recipeCalculated.setPortionProductionCost(portionProductionCost.setScale(2, RoundingMode.HALF_UP));

    return recipeCalculated;
  }

  public BigDecimal calculatePortionSellingPrice(UUID recipeId, BigDecimal profitPercentage) throws Exception {
    var portionProductionCost = this.calculatePortionProductionCost(recipeId).getPortionProductionCost();

    BigDecimal portionProfit = portionProductionCost.multiply(profitPercentage).divide(
        (BigDecimal.valueOf(100)), RoundingMode.HALF_UP);

    BigDecimal portionSellingPrice = portionProductionCost.add(portionProfit);

    return portionSellingPrice.setScale(2, RoundingMode.HALF_UP);
  }

  private boolean profitPercentageIsGreaterThanZero(BigDecimal profitPercentage) {
    return profitPercentage.compareTo(new BigDecimal(0.0)) > 0;
  }
}
