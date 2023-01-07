package com.jovicsantos.suneverapi.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MeasurementDto {

  @NotBlank
  private String name;
  @NotBlank
  @Length(max = 10)
  private String abbreviation;
}
