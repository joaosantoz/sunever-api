package com.jovicsantos.suneverapi.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private RecipeIngredientPK id;

  @ManyToOne
  @MapsId("recipe_id")
  @JoinColumn(name = "recipe_id")
  private RecipeModel recipe;

  @ManyToOne
  @MapsId("ingredient_id")
  @JoinColumn(name = "ingredient_id")
  private IngredientModel ingredient;

  @Column(nullable = false)
  private float quantity;
}
