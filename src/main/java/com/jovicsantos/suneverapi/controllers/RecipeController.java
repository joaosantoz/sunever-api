package com.jovicsantos.suneverapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovicsantos.suneverapi.dtos.RecipeDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
  @PostMapping
  public void saveRecipe(@RequestBody @Valid RecipeDto recipe) {
    System.out.println(recipe);
  }
}
