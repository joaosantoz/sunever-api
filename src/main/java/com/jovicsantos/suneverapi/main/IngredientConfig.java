package com.jovicsantos.suneverapi.main;

import com.jovicsantos.suneverapi.api.dto.IngredientDto;
import com.jovicsantos.suneverapi.application.gateway.IngredientGateway;
import com.jovicsantos.suneverapi.application.interactor.IngredientInteractor;
import com.jovicsantos.suneverapi.infrastructure.gateway.IngredientRepositoryGateway;
import com.jovicsantos.suneverapi.infrastructure.persistance.mapper.IngredientMapper;
import com.jovicsantos.suneverapi.infrastructure.repository.IngredientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredientConfig {
	@Bean
	IngredientInteractor ingredientInteractor(IngredientGateway ingredientGateway) {
		return new IngredientInteractor(ingredientGateway);
	}

	@Bean
	IngredientGateway ingredientGateway(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
		return new IngredientRepositoryGateway(ingredientRepository, ingredientMapper);
	}

	@Bean
	IngredientMapper ingredientMapper() {
		return new IngredientMapper();
	}

	@Bean
	IngredientDto ingredientDtoMapper() {
		return new IngredientDto();
	}
}
