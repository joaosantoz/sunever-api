package com.jovicsantos.suneverapi.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RecipeIngredientsDto(@NotNull UUID id, @NotNull BigDecimal quantity) {
}
