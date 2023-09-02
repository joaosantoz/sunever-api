package com.jovicsantos.suneverapi.infrastructure.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "recipe_ingredient")
public class RecipeIngredientEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private UUID recipeIngredientId;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipe_id")
	private RecipeEntity recipe;

	@Column(nullable = false, name = "ingredient_id")
	private UUID id;

	@Column(nullable = false, name = "ingredient_quantity")
	private BigDecimal quantity;
}
