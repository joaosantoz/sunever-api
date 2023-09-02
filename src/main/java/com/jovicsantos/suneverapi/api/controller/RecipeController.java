//package com.jovicsantos.suneverapi.api.controller;
//
//import com.jovicsantos.suneverapi.api.dto.RecipeDto;
//import com.jovicsantos.suneverapi.api.dto.RecipeIngredientsDto;
//import com.jovicsantos.suneverapi.application.interactor.IngredientInteractor;
//import com.jovicsantos.suneverapi.application.interactor.RecipeInteractor;
//import com.jovicsantos.suneverapi.infrastructure.persistance.entity.IngredientEntity;
//import com.jovicsantos.suneverapi.infrastructure.persistance.entity.RecipeEntity;
//import com.jovicsantos.suneverapi.infrastructure.persistance.entity.RecipeIngredientEntity;
//import jakarta.validation.Valid;
//import org.springframework.beans.BeanUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@RestController
//@RequestMapping("/recipes")
//public class RecipeController {
//	private final RecipeInteractor recipeInteractor;
//
//	private final IngredientInteractor ingredientInteractor;
//
//	public RecipeController(RecipeInteractor recipeInteractor, IngredientInteractor ingredientInteractor) {
//		this.recipeInteractor = recipeInteractor;
//		this.ingredientInteractor = ingredientInteractor;
//	}
//
//	@PostMapping
//	public ResponseEntity<Object> saveRecipe(@RequestBody @Valid RecipeDto recipeDto) throws Exception {
//		if (recipeInteractor.existsByName(recipeDto.name())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This recipe already exists.");
//		}
//
//		var recipeModel = new RecipeEntity();
//		BeanUtils.copyProperties(recipeDto, recipeModel);
//
//		List<RecipeIngredientsDto> recipeIngredientsList = recipeDto.ingredients();
//
//		ArrayList<RecipeIngredientEntity> ingredientList = new ArrayList<>();
//
//		for (RecipeIngredientsDto ingredient : recipeIngredientsList) {
//			Optional<IngredientEntity> ingredientOptional = ingredientInteractor.findById(ingredient.id());
//
//			if (ingredientOptional.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient " + ingredient.id() + " not found.");
//			}
//
//			var recipeIngredientModel = new RecipeIngredientEntity();
//
//			recipeIngredientModel.setId(ingredient.id());
//			recipeIngredientModel.setQuantity(ingredient.quantity());
//
//			ingredientList.add(recipeIngredientModel);
//		}
//
//		recipeModel.setIngredientList(ingredientList);
//		var savedRecipe = recipeInteractor.save(recipeModel, ingredientList);
//		recipeInteractor.calculateRecipeProductionCost(savedRecipe.getId());
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(recipeInteractor.calculatePortionProductionCost(savedRecipe.getId()));
//	}
//
//	@GetMapping("/calculate/{recipeId}")
//	public ResponseEntity<Map<String, BigDecimal>> calculate(@PathVariable UUID recipeId, @RequestParam() BigDecimal profitPercentage) throws Exception {
//		Map<String, BigDecimal> sellingPricesMap = new HashMap<>();
//
//		var recipeSellingPriceValue = recipeInteractor.calculateRecipeSellingPrice(recipeId, profitPercentage);
//		var portionSellingPrice = recipeInteractor.calculatePortionSellingPrice(recipeId, profitPercentage);
//
//		sellingPricesMap.put("recipeSellingPrice", recipeSellingPriceValue);
//		sellingPricesMap.put("portionSellingPrice", portionSellingPrice);
//
//		return ResponseEntity.status(HttpStatus.OK).body(sellingPricesMap);
//	}
//}
