package com.jovicsantos.suneverapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovicsantos.suneverapi.dtos.RecipeDto;
import com.jovicsantos.suneverapi.dtos.RecipeIngredientsDto;
import com.jovicsantos.suneverapi.models.Ingredient;
import com.jovicsantos.suneverapi.models.Recipe;
import com.jovicsantos.suneverapi.models.RecipeIngredient;
import com.jovicsantos.suneverapi.services.IngredientService;
import com.jovicsantos.suneverapi.services.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
  @Autowired
  RecipeService recipeService;

  @Autowired
  IngredientService ingredientService;

  @PostMapping
  public ResponseEntity<Object> saveRecipe(@RequestBody @Valid RecipeDto recipeDto) {
    if (recipeService.existsByName(recipeDto.name())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This recipe already exists.");
    }

    var recipeModel = new Recipe();
    BeanUtils.copyProperties(recipeDto, recipeModel);

    List<RecipeIngredientsDto> recipeIngredientsList = recipeDto.ingredients();

    ArrayList<RecipeIngredient> ingredientList = new ArrayList<>();

    for (RecipeIngredientsDto ingredient : recipeIngredientsList) {
      Optional<Ingredient> ingredientOptional = ingredientService.findById(ingredient.id());

      if (ingredientOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient " + ingredient.id() + " not found.");
      }

      var recipeIngredientModel = new RecipeIngredient();

      recipeIngredientModel.setIngredientId(ingredient.id());
      recipeIngredientModel.setIngredientQuantity(ingredient.quantity());

      ingredientList.add(recipeIngredientModel);
    }

    recipeModel.setIngredientList(ingredientList);

    return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.save(recipeModel, ingredientList));
  }
}
