package com.jovicsantos.suneverapi.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RecipeIngredientPK implements Serializable {
  @Column(name = "recipe_id")
  private UUID recipeId;

  @Column(name = "ingredient_id")
  private UUID ingredientId;
}