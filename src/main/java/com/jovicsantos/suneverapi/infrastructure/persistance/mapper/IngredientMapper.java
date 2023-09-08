package com.jovicsantos.suneverapi.infrastructure.persistance.mapper;

import com.jovicsantos.suneverapi.domain.Ingredient;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;

public class IngredientMapper {
	public IngredientEntity toEntity(Ingredient ingredient) {
		return new IngredientEntity(
						ingredient.getId(),
						ingredient.getName(),
						ingredient.getPrice(),
						ingredient.getQuantityPerMeasure(),
						ingredient.getMeasurement()
		);
	}

	public Ingredient toDomain(IngredientEntity ingredientEntity) {
		return new Ingredient(
						ingredientEntity.getId(),
						ingredientEntity.getName(),
						ingredientEntity.getPrice(),
						ingredientEntity.getQuantityPerMeasure(),
						ingredientEntity.getMeasurement()
		);
	}
}
