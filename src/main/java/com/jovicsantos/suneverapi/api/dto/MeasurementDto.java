package com.jovicsantos.suneverapi.api.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record MeasurementDto(
		@NotBlank String name,
		@NotBlank @Length(max = 10) String abbreviation) {
}