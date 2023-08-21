package com.jovicsantos.suneverapi.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RecipeIngredientsDto(@NotNull UUID id, @NotNull BigDecimal quantity) {
}
