package com.jovicsantos.suneverapi.application.output;

import com.jovicsantos.suneverapi.domain.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class IngredientOutput {
	private UUID id;
	private String name;
	private BigDecimal price;
	private BigDecimal quantityPerMeasure;
	private Measurement measurement;
}
