package com.jovicsantos.suneverapi.infrastructure.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class RecipeEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String imageLink;
	@Column(nullable = false)
	private Integer portions;
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeIngredientEntity> ingredientList;
	@Transient
	private BigDecimal recipeProductionCost;
	@Transient
	private BigDecimal portionProductionCost;
	@JsonIgnore
	@Transient
	private BigDecimal recipeSellingPrice;
}
