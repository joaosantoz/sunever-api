package com.jovicsantos.suneverapi.dtos;

import java.util.List;

public record RecipeDto(String name, String description, List<RecipeIngredientsDto> ingredients) {
}
