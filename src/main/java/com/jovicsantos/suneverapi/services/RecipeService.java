package com.jovicsantos.suneverapi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.models.Ingredient;
import com.jovicsantos.suneverapi.models.Recipe;
import com.jovicsantos.suneverapi.models.RecipeIngredient;
import com.jovicsantos.suneverapi.repositories.RecipeIngredientRepository;
import com.jovicsantos.suneverapi.repositories.RecipeRepository;

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

  public Recipe calculateRecipeCost(UUID recipeId) throws Exception {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (!recipeOptional.isPresent()) {
      throw new Exception("Recipe " + recipeId + " not found.");
    }

    var recipe = recipeOptional.get();

    var recipeProductionCost = this.calculateAllRecipeIngredientsCost(recipe);

    recipe.setRecipeProductionCost(recipeProductionCost);

    return recipe;
  }

  private BigDecimal calculateAllRecipeIngredientsCost(Recipe recipe) throws Exception {
    List<BigDecimal> ingredientsCostList = new ArrayList<>();

    for (RecipeIngredient recipeIngredient : recipe.getIngredientList()) {

      UUID ingredientId = recipeIngredient.getIngredientId();

      Optional<Ingredient> ingredientOptional = ingredientService.findById(ingredientId);

      if (!ingredientOptional.isPresent()) {
        throw new Exception("Ingredient " + ingredientId + " not found.");
      }

      var ingredient = ingredientOptional.get();

      BigDecimal ingredientQuantityPerRecipe = recipeIngredient.getIngredientQuantity();
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
    var ingredientCostOfOneMeasure = ingredientQuantityPerMeasure.divide(ingredientPrice, 2, RoundingMode.HALF_UP);
    var ingredientCostRecipe = ingredientQuantityPerRecipe.divide(ingredientCostOfOneMeasure, 2, RoundingMode.HALF_UP);

    return ingredientCostRecipe;
  }

  public BigDecimal calculateRecipeSellingPrice(UUID recipeId, BigDecimal profitPercentage) throws Exception {
    if (!this.profitPercentageIsGreaterThanZero(profitPercentage)) {
      throw new Exception("The field profitPercentage must be greater than zero.");
    }

    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    if (!recipeOptional.isPresent()) {
      throw new Exception("Recipe " + recipeId + " not found.");
    }

    var recipe = recipeOptional.get();

    var recipeProductionCost = recipe.getRecipeProductionCost();

    BigDecimal recipeProfit = recipeProductionCost.multiply(profitPercentage).divide((BigDecimal.valueOf(100)), 2,
        RoundingMode.HALF_UP);

    BigDecimal recipeSellingPrice = recipeProductionCost.add(recipeProfit);

    return recipeSellingPrice;
  }

  private boolean profitPercentageIsGreaterThanZero(BigDecimal profitPercentage) {
    return profitPercentage.compareTo(new BigDecimal(0.0)) > 0;
  }
}
