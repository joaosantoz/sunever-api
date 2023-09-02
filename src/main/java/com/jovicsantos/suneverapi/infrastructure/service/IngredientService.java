package com.jovicsantos.suneverapi.infrastructure.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;

@Service
public class IngredientService {
  @Autowired
  IngredientRepository ingredientRepository;

  public boolean existsByName(String name) {
    return ingredientRepository.existsByName(name);
  }

  public IngredientEntity save(IngredientEntity ingredient) {
    return ingredientRepository.save(ingredient);
  }

  public Iterable<IngredientEntity> findAll() {
    return ingredientRepository.findAll();
  }

  public Iterable<IngredientEntity> findAllById(Iterable<UUID> ids) {
    return ingredientRepository.findAllById(ids);
  }

  public Optional<IngredientEntity> findById(UUID id) {
    return ingredientRepository.findById(id);
  }

  public void deleteById(UUID id) {
    ingredientRepository.deleteById(id);
  }
}
