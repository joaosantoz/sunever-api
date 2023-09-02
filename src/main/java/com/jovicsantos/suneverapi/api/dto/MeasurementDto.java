package com.jovicsantos.suneverapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record MeasurementDto(@NotBlank String name, @NotBlank @Length(max = 10) String abbreviation) {
}