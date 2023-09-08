package com.jovicsantos.suneverapi.api.dto;

import com.jovicsantos.suneverapi.application.input.IngredientInput;
import com.jovicsantos.suneverapi.application.interactor.MeasurementInteractor;
import com.jovicsantos.suneverapi.application.output.IngredientOutput;
import com.jovicsantos.suneverapi.domain.Ingredient;
import com.jovicsantos.suneverapi.domain.Measurement;

public class IngredientDto {
	private MeasurementInteractor measurementInteractor;

	public IngredientDto(MeasurementInteractor measurementInteractor) {
		this.measurementInteractor = measurementInteractor;
	}

	public Ingredient toDomain(IngredientInput ingredientInput) {
		Measurement measurement = measurementInteractor.find(ingredientInput.getMeasurementId());

		return new Ingredient(null, ingredientInput.getName(), ingredientInput.getPrice(), ingredientInput.getQuantityPerMeasure(), measurement);
	}

	public IngredientOutput toOutput(Ingredient ingredient) {
		Measurement measurement = measurementInteractor.find(ingredient.getId());

		return new IngredientOutput(ingredient.getId(), ingredient.getName(), ingredient.getPrice(), ingredient.getQuantityPerMeasure(), measurement);
	}
}
