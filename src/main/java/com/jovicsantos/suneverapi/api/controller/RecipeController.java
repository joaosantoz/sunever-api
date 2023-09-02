package com.jovicsantos.suneverapi.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovicsantos.suneverapi.api.dto.RecipeDto;
import com.jovicsantos.suneverapi.api.dto.RecipeIngredientsDto;
import com.jovicsantos.suneverapi.infrastructure.db.entity.IngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeIngredientEntity;
import com.jovicsantos.suneverapi.infrastructure.db.entity.RecipeEntity;
import com.jovicsantos.suneverapi.infrastructure.service.IngredientService;
import com.jovicsantos.suneverapi.infrastructure.service.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
  @Autowired
  RecipeService recipeService;

  @Autowired
  IngredientService ingredientService;

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

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(recipeService.calculatePortionProductionCost(savedRecipe.getId()));
  }

  @GetMapping("/calculate/{recipeId}")
  public ResponseEntity<Map<String, BigDecimal>> calculate(@PathVariable UUID recipeId,
      @RequestParam(required = true) BigDecimal profitPercentage) throws Exception {
    Map<String, BigDecimal> sellingPricesMap = new HashMap<String, BigDecimal>();

    var recipeSellingPriceValue = recipeService.calculateRecipeSellingPrice(recipeId, profitPercentage);
    var portionSellingPrice = recipeService.calculatePortionSellingPrice(recipeId, profitPercentage);

    sellingPricesMap.put("recipeSellingPrice", recipeSellingPriceValue);
    sellingPricesMap.put("portionSellingPrice", portionSellingPrice);

    return ResponseEntity.status(HttpStatus.OK).body(sellingPricesMap);
  }
}
