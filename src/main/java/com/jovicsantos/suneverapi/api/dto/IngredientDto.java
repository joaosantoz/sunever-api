package com.jovicsantos.suneverapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record IngredientDto(@NotBlank String name, @NotNull BigDecimal price, @NotNull BigDecimal quantityPerMeasure,
														@NotNull UUID measurementId) {
}
