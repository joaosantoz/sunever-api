package com.jovicsantos.suneverapi.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeIngredient(UUID recipeIngredientId, Recipe recipe, UUID id, BigDecimal quantity) {
}
