package com.jovicsantos.suneverapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.models.Recipe;
import com.jovicsantos.suneverapi.repositories.RecipeRepository;

@Service
public class RecipeService {
  @Autowired
  private RecipeRepository recipeRepository;

  public Recipe save(Recipe recipe) {
    return recipeRepository.save(recipe);
  }
}
