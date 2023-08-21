package com.jovicsantos.suneverapi.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class Ingredient {
  private UUID id;
  private String name;
  private BigDecimal price;
  private BigDecimal quantityPerMeasure;
  private Measurement measurement;
}
