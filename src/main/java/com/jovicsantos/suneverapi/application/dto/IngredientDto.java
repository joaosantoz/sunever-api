package com.jovicsantos.suneverapi.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientDto(
		@NotBlank String name,
		@NotNull BigDecimal price,
		@NotNull BigDecimal quantityPerMeasure,
		@NotNull UUID measurementId) {
}
