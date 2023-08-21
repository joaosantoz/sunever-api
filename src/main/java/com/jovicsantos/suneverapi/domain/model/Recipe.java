package com.jovicsantos.suneverapi.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Recipe {
  private UUID id;
  private String name;
  private String description;
  private String imageLink;
  private Integer portions;
  private List<RecipeIngredient> ingredientList;
  private BigDecimal recipeProductionCost;
  private BigDecimal portionProductionCost;
  private BigDecimal recipeSellingPrice;
}