package com.jovicsantos.suneverapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovicsantos.suneverapi.dtos.RecipeDto;
import com.jovicsantos.suneverapi.models.Recipe;
import com.jovicsantos.suneverapi.models.RecipeIngredient;
import com.jovicsantos.suneverapi.services.IngredientService;
import com.jovicsantos.suneverapi.services.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
  @Autowired
  private RecipeService recipeService;
  @Autowired
  private IngredientService ingredientService;

  @PostMapping
  public Object saveRecipe(@RequestBody @Valid RecipeDto recipeDto) {
    Recipe recipeModel = new Recipe();
    BeanUtils.copyProperties(recipeDto, recipeModel);

    List<RecipeIngredient> ingredients = new ArrayList<>();

    recipeDto.ingredients().forEach(ingredientDtoItem -> {
      RecipeIngredient recipeIngredientModel = new RecipeIngredient();

      var ingredient = ingredientService.findById(ingredientDtoItem.id()).get();
      var quantity = ingredientDtoItem.quantity();

      recipeIngredientModel.setIngredient(ingredient);
      recipeIngredientModel.setQuantity(quantity);

      ingredients.add(recipeIngredientModel);
    });

    recipeModel.setIngredients(ingredients);

    return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.save(recipeModel));
  }
}
