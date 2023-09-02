package com.jovicsantos.suneverapi.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class RecipeIngredient {
  private UUID recipeIngredientId;
  private Recipe recipe;
  private UUID id;
  private BigDecimal quantity;
}
