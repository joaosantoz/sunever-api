package com.jovicsantos.suneverapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
