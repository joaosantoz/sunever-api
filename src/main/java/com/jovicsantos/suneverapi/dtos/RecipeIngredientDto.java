package com.jovicsantos.suneverapi.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RecipeIngredientDto(@NotNull UUID ingredientId, @NotNull BigDecimal quantityPerRecipe) {
}
