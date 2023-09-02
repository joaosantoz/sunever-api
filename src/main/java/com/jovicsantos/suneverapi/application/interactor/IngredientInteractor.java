package com.jovicsantos.suneverapi.application.interactor;

import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;

import java.util.Optional;
import java.util.UUID;

public class IngredientInteractor {
	private final IngredientRepository ingredientRepository;

	public IngredientInteractor(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	public boolean existsByName(String name) {
		return ingredientRepository.existsByName(name);
	}

	public IngredientEntity save(IngredientEntity ingredient) {
		return ingredientRepository.save(ingredient);
	}

	public Iterable<IngredientEntity> findAll() {
		return ingredientRepository.findAll();
	}

	public Optional<IngredientEntity> findById(UUID id) {
		return ingredientRepository.findById(id);
	}

	public void deleteById(UUID id) {
		ingredientRepository.deleteById(id);
	}
}
