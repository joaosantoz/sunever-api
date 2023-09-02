package com.jovicsantos.suneverapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeDto(@NotBlank String name, @NotBlank String description, @NotBlank String imageLink,
												@NotNull Integer portions,
												@NotNull List<RecipeIngredientsDto> ingredients) {
}
