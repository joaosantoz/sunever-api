package com.jovicsantos.suneverapi.infrastructure.db.entity;

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
@Table(name = "ingredient")
public class IngredientEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal quantityPerMeasure;

	@ManyToOne
	@JoinColumn(name = "measurement_id")
	private MeasurementEntity measurement;
}
