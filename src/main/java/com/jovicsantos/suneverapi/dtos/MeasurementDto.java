package com.jovicsantos.suneverapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MeasurementDto {

  @NotBlank
  private String name;
  @NotBlank
  private String description;
}
