package com.jovicsantos.suneverapi.infrastructure.gateway;

import com.jovicsantos.suneverapi.application.gateway.IngredientGateway;
import com.jovicsantos.suneverapi.domain.Ingredient;
import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.persistance.mapper.IngredientMapper;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IngredientRepositoryGateway implements IngredientGateway {
	private final IngredientRepository ingredientRepository;
	private final IngredientMapper ingredientMapper;

	public IngredientRepositoryGateway(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
		this.ingredientRepository = ingredientRepository;
		this.ingredientMapper = ingredientMapper;
	}

	@Override
	public Ingredient createIngredient(Ingredient ingredient) {
		IngredientEntity ingredientEntity = ingredientMapper.toEntity(ingredient);
		IngredientEntity ingredientSaved = ingredientRepository.save(ingredientEntity);

		return ingredientMapper.toDomain(ingredientSaved);
	}

	@Override
	public Optional<Ingredient> findIngredient(UUID id) {
		Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

		return ingredientEntity.map(ingredientMapper::toDomain);
	}

	@Override
	public List<Ingredient> findAllIngredients() {
		List<IngredientEntity> ingredientEntities = ingredientRepository.findAll();
		if (ingredientEntities.isEmpty()) {
			return Collections.emptyList();
		}
		return ingredientEntities.stream().map(ingredientMapper::toDomain).toList();
	}

	@Override
	public Ingredient updateIngredient(UUID id, Ingredient ingredient) {
		IngredientEntity ingredientEntity = ingredientMapper.toEntity(ingredient);
		IngredientEntity ingredientUpdated = ingredientRepository.save(ingredientEntity);

		return ingredientMapper.toDomain(ingredientUpdated);
	}

	@Override
	public void deleteIngredient(UUID id) {
		ingredientRepository.deleteById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return ingredientRepository.existsByName(name);
	}
}
