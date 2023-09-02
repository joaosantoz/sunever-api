package com.jovicsantos.suneverapi.infrastructure.service;

import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {
	final IngredientRepository ingredientRepository;

	public IngredientService(IngredientRepository ingredientRepository) {
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
