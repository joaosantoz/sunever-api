package com.jovicsantos.suneverapi.api.controller;

import com.jovicsantos.suneverapi.api.dto.RecipeDto;
import com.jovicsantos.suneverapi.api.dto.RecipeIngredientsDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeEntity;
import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeIngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.service.IngredientService;
import com.jovicsantos.suneverapi.infrastructure.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	final RecipeService recipeService;

	final IngredientService ingredientService;

	public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@PostMapping
	public ResponseEntity<Object> saveRecipe(@RequestBody @Valid RecipeDto recipeDto) throws Exception {
		if (recipeService.existsByName(recipeDto.name())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This recipe already exists.");
		}

		var recipeModel = new RecipeEntity();
		BeanUtils.copyProperties(recipeDto, recipeModel);

		List<RecipeIngredientsDto> recipeIngredientsList = recipeDto.ingredients();

		ArrayList<RecipeIngredientEntity> ingredientList = new ArrayList<>();

		for (RecipeIngredientsDto ingredient : recipeIngredientsList) {
			Optional<IngredientEntity> ingredientOptional = ingredientService.findById(ingredient.id());

			if (ingredientOptional.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient " + ingredient.id() + " not found.");
			}

			var recipeIngredientModel = new RecipeIngredientEntity();

			recipeIngredientModel.setId(ingredient.id());
			recipeIngredientModel.setQuantity(ingredient.quantity());

			ingredientList.add(recipeIngredientModel);
		}

		recipeModel.setIngredientList(ingredientList);
		var savedRecipe = recipeService.save(recipeModel, ingredientList);
		recipeService.calculateRecipeProductionCost(savedRecipe.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.calculatePortionProductionCost(savedRecipe.getId()));
	}

	@GetMapping("/calculate/{recipeId}")
	public ResponseEntity<Map<String, BigDecimal>> calculate(@PathVariable UUID recipeId, @RequestParam() BigDecimal profitPercentage) throws Exception {
		Map<String, BigDecimal> sellingPricesMap = new HashMap<>();

		var recipeSellingPriceValue = recipeService.calculateRecipeSellingPrice(recipeId, profitPercentage);
		var portionSellingPrice = recipeService.calculatePortionSellingPrice(recipeId, profitPercentage);

		sellingPricesMap.put("recipeSellingPrice", recipeSellingPriceValue);
		sellingPricesMap.put("portionSellingPrice", portionSellingPrice);

		return ResponseEntity.status(HttpStatus.OK).body(sellingPricesMap);
	}
}
