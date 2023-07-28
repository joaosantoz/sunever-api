package com.jovicsantos.suneverapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ingredient")
public class Ingredient implements Serializable {
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
  private Measurement measurement;
}
