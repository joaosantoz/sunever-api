package com.jovicsantos.suneverapi.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record Recipe(UUID id, String name, String description, String imageLink, Integer portions,
										 List<RecipeIngredient> ingredientList, BigDecimal recipeProductionCost,
										 BigDecimal portionProductionCost, BigDecimal recipeSellingPrice) {
}
