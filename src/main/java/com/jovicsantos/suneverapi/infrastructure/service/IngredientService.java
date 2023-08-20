package com.jovicsantos.suneverapi.infrastructure.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.domain.entity.Ingredient;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;

@Service
public class IngredientService {
  @Autowired
  IngredientRepository ingredientRepository;

  public boolean existsByName(String name) {
    return ingredientRepository.existsByName(name);
  }

  public Ingredient save(Ingredient ingredient) {
    return ingredientRepository.save(ingredient);
  }

  public Iterable<Ingredient> findAll() {
    return ingredientRepository.findAll();
  }

  public Iterable<Ingredient> findAllById(Iterable<UUID> ids) {
    return ingredientRepository.findAllById(ids);
  }

  public Optional<Ingredient> findById(UUID id) {
    return ingredientRepository.findById(id);
  }

  public void deleteById(UUID id) {
    ingredientRepository.deleteById(id);
  }
}
