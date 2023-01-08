package com.jovicsantos.suneverapi.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientDto(
		@NotBlank String name,
		@NotNull BigDecimal price,
		@NotNull BigDecimal quantity_per_measure,
		@NotNull UUID measurement_id) {
}
